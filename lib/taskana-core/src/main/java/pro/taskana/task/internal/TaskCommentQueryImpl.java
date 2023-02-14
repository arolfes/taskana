package pro.taskana.task.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;

import pro.taskana.common.api.TaskanaRole;
import pro.taskana.common.api.TimeInterval;
import pro.taskana.common.internal.InternalTaskanaEngine;
import pro.taskana.task.api.TaskCommentQuery;
import pro.taskana.task.api.TaskCommentQueryColumnName;
import pro.taskana.task.api.exceptions.TaskNotFoundException;
import pro.taskana.task.api.models.TaskComment;
import pro.taskana.workbasket.api.exceptions.MismatchedWorkbasketPermissionException;
import pro.taskana.workbasket.api.exceptions.NotAuthorizedToQueryWorkbasketException;
import pro.taskana.workbasket.internal.WorkbasketQueryImpl;

/** TaskCommentQuery for generating dynamic sql. */
@Slf4j
public class TaskCommentQueryImpl implements TaskCommentQuery {

  private static final String LINK_TO_MAPPER =
      "pro.taskana.task.internal.TaskCommentQueryMapper.queryTaskComments";

  private static final String LINK_TO_VALUE_MAPPER =
      "pro.taskana.task.internal.TaskCommentQueryMapper.queryTaskCommentColumnValues";

  private static final String LINK_TO_COUNTER =
      "pro.taskana.task.internal.TaskCommentQueryMapper.countQueryTaskComments";

  private final InternalTaskanaEngine taskanaEngine;
  private final TaskServiceImpl taskService;
  private final List<String> orderBy;
  private final List<String> orderColumns;
  @Getter private TaskCommentQueryColumnName queryColumnName;
  @Getter private String[] idIn;
  @Getter private String[] idNotIn;
  @Getter private String[] idLike;
  @Getter private String[] idNotLike;
  @Getter private String[] taskIdIn;
  @Getter private String[] creatorIn;
  @Getter private String[] creatorNotIn;
  @Getter private String[] creatorLike;
  @Getter private String[] creatorNotLike;
  @Getter private String[] textFieldLike;
  @Getter private String[] textFieldNotLike;
  @Getter private TimeInterval[] modifiedIn;
  @Getter private TimeInterval[] modifiedNotIn;
  @Getter private TimeInterval[] createdIn;
  @Getter private TimeInterval[] createdNotIn;
  @Getter private String[] accessIdIn;
  @Getter private boolean joinWithUserInfo;

  TaskCommentQueryImpl(InternalTaskanaEngine taskanaEngine) {
    this.taskanaEngine = taskanaEngine;
    this.taskService = (TaskServiceImpl) taskanaEngine.getEngine().getTaskService();
    this.orderBy = new ArrayList<>();
    this.orderColumns = new ArrayList<>();
    this.joinWithUserInfo = taskanaEngine.getEngine().getConfiguration().isAddAdditionalUserInfo();
  }

  @Override
  public TaskCommentQuery idIn(String... taskCommentIds) {
    this.idIn = taskCommentIds;
    return this;
  }

  @Override
  public TaskCommentQuery idNotIn(String... taskCommentIds) {
    this.idNotIn = taskCommentIds;
    return this;
  }

  @Override
  public TaskCommentQuery idLike(String... taskCommentIds) {
    this.idLike = toLowerCopy(taskCommentIds);
    return this;
  }

  @Override
  public TaskCommentQuery idNotLike(String... taskCommentIds) {
    this.idNotLike = toLowerCopy(taskCommentIds);
    return this;
  }

  @Override
  public TaskCommentQuery taskIdIn(String... taskIds) {
    this.taskIdIn = taskIds;
    return this;
  }

  @Override
  public TaskCommentQuery textFieldLike(String... texts) {
    this.textFieldLike = toLowerCopy(texts);
    return this;
  }

  @Override
  public TaskCommentQuery textFieldNotLike(String... texts) {
    this.textFieldNotLike = toLowerCopy(texts);
    return this;
  }

  @Override
  public TaskCommentQuery creatorIn(String... creators) {
    this.creatorIn = creators;
    return this;
  }

  @Override
  public TaskCommentQuery creatorNotIn(String... creators) {
    this.creatorNotIn = creators;
    return this;
  }

  @Override
  public TaskCommentQuery creatorLike(String... creators) {
    this.creatorLike = toLowerCopy(creators);
    return this;
  }

  @Override
  public TaskCommentQuery creatorNotLike(String... creators) {
    this.creatorNotLike = toLowerCopy(creators);
    return this;
  }

  @Override
  public TaskCommentQuery createdWithin(TimeInterval... intervals) {
    validateAllIntervals(intervals);
    this.createdIn = intervals;
    return this;
  }

  @Override
  public TaskCommentQuery createdNotWithin(TimeInterval... intervals) {
    this.createdNotIn = intervals;
    return this;
  }

  @Override
  public TaskCommentQuery modifiedWithin(TimeInterval... intervals) {
    validateAllIntervals(intervals);
    this.modifiedIn = intervals;
    return this;
  }

  @Override
  public TaskCommentQuery modifiedNotWithin(TimeInterval... intervals) {
    this.modifiedNotIn = intervals;
    return this;
  }

  @Override
  public List<TaskComment> list() {
    checkTaskPermission();
    setupAccessIds();
    return taskanaEngine.executeInDatabaseConnection(
        () -> taskanaEngine.getSqlSession().selectList(LINK_TO_MAPPER, this));
  }

  @Override
  public List<TaskComment> list(int offset, int limit) {
    checkTaskPermission();
    setupAccessIds();
    RowBounds rowBounds = new RowBounds(offset, limit);
    return taskanaEngine.executeInDatabaseConnection(
        () -> taskanaEngine.getSqlSession().selectList(LINK_TO_MAPPER, this, rowBounds));
  }

  @Override
  public List<String> listValues(
      TaskCommentQueryColumnName columnName, SortDirection sortDirection) {
    checkTaskPermission();
    setupAccessIds();
    queryColumnName = columnName;
    // TO-DO: order?
    if (columnName == TaskCommentQueryColumnName.CREATOR_FULL_NAME) {
      joinWithUserInfo = true;
    }

    return taskanaEngine.executeInDatabaseConnection(
        () -> taskanaEngine.getSqlSession().selectList(LINK_TO_VALUE_MAPPER, this));
  }

  @Override
  public TaskComment single() {
    checkTaskPermission();
    setupAccessIds();
    return taskanaEngine.executeInDatabaseConnection(
        () -> taskanaEngine.getSqlSession().selectOne(LINK_TO_MAPPER, this));
  }

  @Override
  public long count() {
    checkTaskPermission();
    setupAccessIds();
    Long rowCount =
        taskanaEngine.executeInDatabaseConnection(
            () -> taskanaEngine.getSqlSession().selectOne(LINK_TO_COUNTER, this));
    return (rowCount == null) ? 0L : rowCount;
  }

  @Override
  public TaskCommentQuery orderByCreated(SortDirection sortDirection) {
    return addOrderCriteria("CREATED", sortDirection);
  }

  @Override
  public TaskCommentQuery orderByModified(SortDirection sortDirection) {
    return addOrderCriteria("MODIFIED", sortDirection);
  }

  private void checkTaskPermission() {

    if (taskIdIn != null) {
      if (taskanaEngine.getEngine().isUserInRole(TaskanaRole.ADMIN, TaskanaRole.TASK_ADMIN)) {
        if (log.isDebugEnabled()) {
          log.debug("Skipping permissions check since user is in role ADMIN or TASK_ADMIN.");
        }
        return;
      }

      Arrays.stream(taskIdIn)
          .forEach(
              taskId -> {
                try {
                  taskService.getTask(taskId);
                } catch (MismatchedWorkbasketPermissionException e) {
                  throw new NotAuthorizedToQueryWorkbasketException(
                      e.getMessage(), e.getErrorCode(), e);
                } catch (TaskNotFoundException e) {
                  log.warn(String.format("The Task with the ID ' %s ' does not exist.", taskId), e);
                }
              });
    }
  }

  private TaskCommentQuery addOrderCriteria(String columnName, SortDirection sortDirection) {
    String orderByDirection =
        " " + (sortDirection == null ? SortDirection.ASCENDING : sortDirection);
    orderBy.add(columnName + orderByDirection);
    orderColumns.add(columnName);
    return this;
  }

  private void setupAccessIds() {
    if (taskanaEngine.getEngine().isUserInRole(TaskanaRole.ADMIN, TaskanaRole.TASK_ADMIN)) {
      this.accessIdIn = null;
    } else if (this.accessIdIn == null) {
      String[] accessIds = new String[0];
      List<String> ucAccessIds = taskanaEngine.getEngine().getCurrentUserContext().getAccessIds();
      if (!ucAccessIds.isEmpty()) {
        accessIds = new String[ucAccessIds.size()];
        accessIds = ucAccessIds.toArray(accessIds);
      }
      this.accessIdIn = accessIds;
      WorkbasketQueryImpl.lowercaseAccessIds(this.accessIdIn);
    }
  }

  private void validateAllIntervals(TimeInterval[] intervals) {
    for (TimeInterval ti : intervals) {
      if (!ti.isValid()) {
        throw new IllegalArgumentException("TimeInterval " + ti + " is invalid.");
      }
    }
  }

  @Override
  public String toString() {
    return "TaskCommentQueryImpl [taskanaEngine="
        + taskanaEngine
        + ", taskService="
        + taskService
        + ", queryColumnName="
        + queryColumnName
        + ", idIn="
        + Arrays.toString(idIn)
        + ", idNotIn="
        + Arrays.toString(idNotIn)
        + ", idLike="
        + Arrays.toString(idLike)
        + ", idNotLike="
        + Arrays.toString(idNotLike)
        + ", taskIdIn="
        + Arrays.toString(taskIdIn)
        + ", creatorIn="
        + Arrays.toString(creatorIn)
        + ", creatorNotIn="
        + Arrays.toString(creatorNotIn)
        + ", creatorLike="
        + Arrays.toString(creatorLike)
        + ", creatorNotLike="
        + Arrays.toString(creatorNotLike)
        + ", textFieldLike="
        + Arrays.toString(textFieldLike)
        + ", textFieldNotLike="
        + Arrays.toString(textFieldNotLike)
        + ", modifiedIn="
        + Arrays.toString(modifiedIn)
        + ", modifiedNotIn="
        + Arrays.toString(modifiedNotIn)
        + ", createdIn="
        + Arrays.toString(createdIn)
        + ", createdNotIn="
        + Arrays.toString(createdNotIn)
        + ", accessIdIn="
        + Arrays.toString(accessIdIn)
        + ", joinWithUserInfo="
        + joinWithUserInfo
        + "]";
  }
}
