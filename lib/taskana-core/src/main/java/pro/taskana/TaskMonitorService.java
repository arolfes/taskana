package pro.taskana;

import java.util.List;

import pro.taskana.exceptions.InvalidArgumentException;
import pro.taskana.impl.ClassificationReport;
import pro.taskana.impl.DetailedClassificationReport;
import pro.taskana.impl.Report;
import pro.taskana.impl.ReportLineItemDefinition;
import pro.taskana.impl.SelectedItem;

/**
 * The Task Monitor Service manages operations on tasks regarding the monitoring.
 */
public interface TaskMonitorService {

    /**
     * Returns a {@link Report} grouped by workbaskets. The report contains the total numbers of tasks of the respective
     * workbasket as well as the total number of all tasks. If no filter is required, the respective parameter should be
     * null. The tasks of the report are filtered by workbaskets, states, categories, domains and values of a custom
     * field. Tasks with Timestamp DUE = null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids to filter by workbaskets. To omit this filter, use null for this parameter
     * @param states
     *            a list of states to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field. To omit this filter, use null for this
     *            parameter
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @return the report
     * @throws InvalidArgumentException
     *             thrown if DaysToWorkingDaysConverter is initialized with null
     */
    Report getWorkbasketLevelReport(List<String> workbasketIds, List<TaskState> states, List<String> categories,
        List<String> domains, CustomField customField, List<String> customFieldValues) throws InvalidArgumentException;

    /**
     * Returns a {@link Report} grouped by workbaskets. For each workbasket the report contains the total number of
     * tasks and the number of tasks of the respective cluster that are specified by the
     * {@link ReportLineItemDefinition}s. By default the age of the tasks is counted in working days. Furthermore the
     * Report contains a sum line that contains the total numbers of the different clusters and the total number of all
     * tasks in this report. The tasks of the report are filtered by workbaskets, states, categories, domains and values
     * of a custom field. If no filter is required, the respective parameter should be null. Tasks with Timestamp DUE =
     * null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids objects to filter by workbaskets. To omit this filter, use null for this
     *            parameter
     * @param states
     *            a list of states objects to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field. To omit this filter, use null for this
     *            parameter
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @param reportLineItemDefinitions
     *            a list of reportLineItemDefinitions that specify the subdivision into different cluster of due dates.
     *            Days in past are represented as negative values and days in the future are represented as positive
     *            values. To avoid tasks are counted multiple times or not be listed in the report, these
     *            reportLineItemDefinitions should not overlap and should not have gaps. If the ReportLineDefinition
     *            should represent a single day, lowerLimit and upperLimit have to be equal. The outer cluster of a
     *            report should have open ends. These open ends are represented with Integer.MIN_VALUE and
     *            Integer.MAX_VALUE.
     * @return the report
     * @throws InvalidArgumentException
     *             thrown if DaysToWorkingDaysConverter is initialized with null
     */
    Report getWorkbasketLevelReport(List<String> workbasketIds, List<TaskState> states,
        List<String> categories, List<String> domains, CustomField customField, List<String> customFieldValues,
        List<ReportLineItemDefinition> reportLineItemDefinitions) throws InvalidArgumentException;

    /**
     * Returns a {@link Report} grouped by workbaskets. For each workbasket the report contains the total number of
     * tasks and the number of tasks of the respective cluster that are specified by the
     * {@link ReportLineItemDefinition}s. It can be specified whether the age of the tasks is counted in days or in
     * working days. Furthermore the report contains a sum line that contains the total numbers of the different
     * clusters and the total number of all tasks. The tasks of the report are filtered by workbaskets, states,
     * categories, domains and values of a custom field. If no filter is required, the respective parameter should be
     * null. Tasks with Timestamp DUE = null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids objects to filter by workbaskets. To omit this filter, use null for this
     *            parameter
     * @param states
     *            a list of states objects to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field. To omit this filter, use null for this
     *            parameter
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @param reportLineItemDefinitions
     *            a list of reportLineItemDefinitions that specify the subdivision into different cluster of due dates.
     *            Days in past are represented as negative values and days in the future are represented as positive
     *            values. To avoid tasks are counted multiple times or not be listed in the report, these
     *            reportLineItemDefinitions should not overlap and should not have gaps. If the ReportLineDefinition
     *            should represent a single day, lowerLimit and upperLimit have to be equal. The outer cluster of a
     *            report should have open ends. These open ends are represented with Integer.MIN_VALUE and
     *            Integer.MAX_VALUE.
     * @param inWorkingDays
     *            a boolean parameter that specifies whether the age of the tasks should be counted in days or in
     *            working days
     * @return the report
     * @throws InvalidArgumentException
     *             thrown if DaysToWorkingDaysConverter is initialized with null
     */
    Report getWorkbasketLevelReport(List<String> workbasketIds, List<TaskState> states,
        List<String> categories, List<String> domains, CustomField customField, List<String> customFieldValues,
        List<ReportLineItemDefinition> reportLineItemDefinitions, boolean inWorkingDays)
        throws InvalidArgumentException;

    /**
     * Returns a {@link Report} grouped by categories. The report contains the total numbers of tasks of the respective
     * category as well as the total number of all tasks. The tasks of the report are filtered by workbaskets, states,
     * categories, domains and values of a custom field and values of a custom field. If no filter is required, the
     * respective parameter should be null. Tasks with Timestamp DUE = null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids to filter by workbaskets. To omit this filter, use null for this parameter
     * @param states
     *            a list of states to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field. To omit this filter, use null for this
     *            parameter
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @return the report
     * @throws InvalidArgumentException
     *             thrown if DaysToWorkingDaysConverter is initialized with null
     */
    Report getCategoryReport(List<String> workbasketIds, List<TaskState> states, List<String> categories,
        List<String> domains, CustomField customField, List<String> customFieldValues) throws InvalidArgumentException;

    /**
     * Returns a {@link Report} grouped by categories. For each category the report contains the total number of tasks
     * and the number of tasks of the respective cluster that are specified by the {@link ReportLineItemDefinition}s. By
     * default the age of the tasks is counted in working days. Furthermore the Report contains a sum line that contains
     * the total numbers of the different clusters and the total number of all tasks in this report. The tasks of the
     * report are filtered by workbaskets, states, categories, domains and values of a custom field. If no filter is
     * required, the respective parameter should be null. Tasks with Timestamp DUE = null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids objects to filter by workbaskets. To omit this filter, use null for this
     *            parameter
     * @param states
     *            a list of states objects to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field. To omit this filter, use null for this
     *            parameter
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @param reportLineItemDefinitions
     *            a list of reportLineItemDefinitions that specify the subdivision into different cluster of due dates.
     *            Days in past are represented as negative values and days in the future are represented as positive
     *            values. To avoid tasks are counted multiple times or not be listed in the report, these
     *            reportLineItemDefinitions should not overlap and should not have gaps. If the ReportLineDefinition
     *            should represent a single day, lowerLimit and upperLimit have to be equal. The outer cluster of a
     *            report should have open ends. These open ends are represented with Integer.MIN_VALUE and
     *            Integer.MAX_VALUE.
     * @return the report
     * @throws InvalidArgumentException
     *             thrown if DaysToWorkingDaysConverter is initialized with null
     */
    Report getCategoryReport(List<String> workbasketIds, List<TaskState> states, List<String> categories,
        List<String> domains, CustomField customField, List<String> customFieldValues,
        List<ReportLineItemDefinition> reportLineItemDefinitions) throws InvalidArgumentException;

    /**
     * Returns a {@link Report} grouped by categories. For each category the report contains the total number of tasks
     * and the number of tasks of the respective cluster that are specified by the {@link ReportLineItemDefinition}s. It
     * can be specified whether the age of the tasks is counted in days or in working days. Furthermore the report
     * contains a sum line that contains the total numbers of the different clusters and the total number of all tasks.
     * The tasks of the report are filtered by workbaskets, states, categories, domains and values of a custom field. If
     * no filter is required, the respective parameter should be null. Tasks with Timestamp DUE = null are not
     * considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids objects to filter by workbaskets. To omit this filter, use null for this
     *            parameter
     * @param states
     *            a list of states objects to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field. To omit this filter, use null for this
     *            parameter
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @param reportLineItemDefinitions
     *            a list of reportLineItemDefinitions that specify the subdivision into different cluster of due dates.
     *            Days in past are represented as negative values and days in the future are represented as positive
     *            values. To avoid tasks are counted multiple times or not be listed in the report, these
     *            reportLineItemDefinitions should not overlap and should not have gaps. If the ReportLineDefinition
     *            should represent a single day, lowerLimit and upperLimit have to be equal. The outer cluster of a
     *            report should have open ends. These open ends are represented with Integer.MIN_VALUE and
     *            Integer.MAX_VALUE.
     * @param inWorkingDays
     *            a boolean parameter that specifies whether the age of the tasks should be counted in days or in
     *            working days
     * @return the report
     * @throws InvalidArgumentException
     *             thrown if DaysToWorkingDaysConverter is initialized with null
     */
    Report getCategoryReport(List<String> workbasketIds, List<TaskState> states, List<String> categories,
        List<String> domains, CustomField customField, List<String> customFieldValues,
        List<ReportLineItemDefinition> reportLineItemDefinitions, boolean inWorkingDays)
        throws InvalidArgumentException;

    /**
     * Returns a {@link Classification} grouped by classifications. The report contains the total numbers of tasks of
     * the respective classification as well as the total number of all tasks. The tasks of the report are filtered by
     * workbaskets, states, categories, domains and values of a custom field. If no filter is required, the respective
     * parameter should be null. Tasks with Timestamp DUE = null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids to filter by workbaskets. To omit this filter, use null for this parameter
     * @param states
     *            a list of states to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field. To omit this filter, use null for this
     *            parameter
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @return the ClassificationReport
     * @throws InvalidArgumentException
     *             thrown if DaysToWorkingDaysConverter is initialized with null
     */
    ClassificationReport getClassificationReport(List<String> workbasketIds, List<TaskState> states,
        List<String> categories, List<String> domains, CustomField customField, List<String> customFieldValues)
        throws InvalidArgumentException;

    /**
     * Returns a {@link Classification} grouped by classifications. For each classification the report contains the
     * total number of tasks and the number of tasks of the respective cluster that are specified by the
     * {@link ReportLineItemDefinition}s. By default the age of the tasks is counted in working days. Furthermore the
     * Report contains a sum line that contains the total numbers of the different clusters and the total number of all
     * tasks in this report. The tasks of the report are filtered by workbaskets, states, categories, domains and values
     * of a custom field. If no filter is required, the respective parameter should be null. Tasks with Timestamp DUE =
     * null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids objects to filter by workbaskets. To omit this filter, use null for this
     *            parameter
     * @param states
     *            a list of states objects to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field. To omit this filter, use null for this
     *            parameter
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @param reportLineItemDefinitions
     *            a list of reportLineItemDefinitions that specify the subdivision into different cluster of due dates.
     *            Days in past are represented as negative values and days in the future are represented as positive
     *            values. To avoid tasks are counted multiple times or not be listed in the report, these
     *            reportLineItemDefinitions should not overlap and should not have gaps. If the ReportLineDefinition
     *            should represent a single day, lowerLimit and upperLimit have to be equal. The outer cluster of a
     *            report should have open ends. These open ends are represented with Integer.MIN_VALUE and
     *            Integer.MAX_VALUE.
     * @return the ClassificationReport
     * @throws InvalidArgumentException
     *             thrown if DaysToWorkingDaysConverter is initialized with null
     */
    ClassificationReport getClassificationReport(List<String> workbasketIds, List<TaskState> states,
        List<String> categories, List<String> domains, CustomField customField, List<String> customFieldValues,
        List<ReportLineItemDefinition> reportLineItemDefinitions) throws InvalidArgumentException;

    /**
     * Returns a {@link ClassificationReport} grouped by classification. For each classification the report contains the
     * total number of tasks and the number of tasks of the respective cluster that are specified by the
     * {@link ReportLineItemDefinition}s. It can be specified whether the age of the tasks is counted in days or in
     * working days. Furthermore the report contains a sum line that contains the total numbers of the different
     * clusters and the total number of all tasks. The tasks of the report are filtered by workbaskets, states,
     * categories, domains and values of a custom field. If no filter is required, the respective parameter should be
     * null. Tasks with Timestamp DUE = null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids objects to filter by workbaskets. To omit this filter, use null for this
     *            parameter
     * @param states
     *            a list of states objects to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field. To omit this filter, use null for this
     *            parameter
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @param reportLineItemDefinitions
     *            a list of reportLineItemDefinitions that specify the subdivision into different cluster of due dates.
     *            Days in past are represented as negative values and days in the future are represented as positive
     *            values. To avoid tasks are counted multiple times or not be listed in the report, these
     *            reportLineItemDefinitions should not overlap and should not have gaps. If the ReportLineDefinition
     *            should represent a single day, lowerLimit and upperLimit have to be equal. The outer cluster of a
     *            report should have open ends. These open ends are represented with Integer.MIN_VALUE and
     *            Integer.MAX_VALUE.
     * @param inWorkingDays
     *            a boolean parameter that specifies whether the age of the tasks should be counted in days or in
     *            working days
     * @return the ClassificationReport
     * @throws InvalidArgumentException
     *             thrown if DaysToWorkingDaysConverter is initialized with null
     */
    ClassificationReport getClassificationReport(List<String> workbasketIds, List<TaskState> states,
        List<String> categories, List<String> domains, CustomField customField, List<String> customFieldValues,
        List<ReportLineItemDefinition> reportLineItemDefinitions, boolean inWorkingDays)
        throws InvalidArgumentException;

    /**
     * Returns a {@link DetailedClassificationReport}. The report contains the total numbers of tasks of the respective
     * classification as well as the total number of all tasks. Each ReportLine contains an additional list of
     * ReportLines for the classifications of the attachments of the tasks. The tasks of the report are filtered by
     * workbaskets, states, categories, domains and values of a custom field. If no filter is required, the respective
     * parameter should be null. Tasks with Timestamp DUE = null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids to filter by workbaskets. To omit this filter, use null for this parameter
     * @param states
     *            a list of states to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field. To omit this filter, use null for this
     *            parameter
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @return the DetailedClassificationReport
     * @throws InvalidArgumentException
     *             thrown if DaysToWorkingDaysConverter is initialized with null
     */
    DetailedClassificationReport getDetailedClassificationReport(List<String> workbasketIds, List<TaskState> states,
        List<String> categories, List<String> domains, CustomField customField, List<String> customFieldValues)
        throws InvalidArgumentException;

    /**
     * Returns a {@link DetailedClassificationReport}. For each classification the report contains the total number of
     * tasks and the number of tasks of the respective cluster that are specified by the
     * {@link ReportLineItemDefinition}s. By default the age of the tasks is counted in working days. Each ReportLine
     * contains an additional list of ReportLines for the classifications of the attachments of the tasks. Furthermore
     * the Report contains a sum line that contains the total numbers of the different clusters and the total number of
     * all tasks in this report. The tasks of the report are filtered by workbaskets, states, categories, domains and
     * values of a custom field. If no filter is required, the respective parameter should be null. Tasks with Timestamp
     * DUE = null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids objects to filter by workbaskets. To omit this filter, use null for this
     *            parameter
     * @param states
     *            a list of states objects to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field. To omit this filter, use null for this
     *            parameter
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @param reportLineItemDefinitions
     *            a list of reportLineItemDefinitions that specify the subdivision into different cluster of due dates.
     *            Days in past are represented as negative values and days in the future are represented as positive
     *            values. To avoid tasks are counted multiple times or not be listed in the report, these
     *            reportLineItemDefinitions should not overlap and should not have gaps. If the ReportLineDefinition
     *            should represent a single day, lowerLimit and upperLimit have to be equal. The outer cluster of a
     *            report should have open ends. These open ends are represented with Integer.MIN_VALUE and
     *            Integer.MAX_VALUE.
     * @return the DetailedClassificationReport
     * @throws InvalidArgumentException
     *             thrown if DaysToWorkingDaysConverter is initialized with null
     */
    DetailedClassificationReport getDetailedClassificationReport(List<String> workbasketIds, List<TaskState> states,
        List<String> categories, List<String> domains, CustomField customField, List<String> customFieldValues,
        List<ReportLineItemDefinition> reportLineItemDefinitions) throws InvalidArgumentException;

    /**
     * Returns a {@link DetailedClassificationReport}. For each classification the report contains the total number of
     * tasks and the number of tasks of the respective cluster that are specified by the
     * {@link ReportLineItemDefinition}s. It can be specified whether the age of the tasks is counted in days or in
     * working days. Each ReportLine contains an additional list of ReportLines for the classifications of the
     * attachments of the tasks. Furthermore the report contains a sum line that contains the total numbers of the
     * different clusters and the total number of all tasks. The tasks of the report are filtered by workbaskets,
     * states, categories, domains and values of a custom field. If no filter is required, the respective parameter
     * should be null. Tasks with Timestamp DUE = null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids objects to filter by workbaskets. To omit this filter, use null for this
     *            parameter
     * @param states
     *            a list of states objects to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field. To omit this filter, use null for this
     *            parameter
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @param reportLineItemDefinitions
     *            a list of reportLineItemDefinitions that specify the subdivision into different cluster of due dates.
     *            Days in past are represented as negative values and days in the future are represented as positive
     *            values. To avoid tasks are counted multiple times or not be listed in the report, these
     *            reportLineItemDefinitions should not overlap and should not have gaps. If the ReportLineDefinition
     *            should represent a single day, lowerLimit and upperLimit have to be equal. The outer cluster of a
     *            report should have open ends. These open ends are represented with Integer.MIN_VALUE and
     *            Integer.MAX_VALUE.
     * @param inWorkingDays
     *            a boolean parameter that specifies whether the age of the tasks should be counted in days or in
     *            working days
     * @return the DetailedClassificationReport
     * @throws InvalidArgumentException
     *             thrown if DaysToWorkingDaysConverter is initialized with null
     */
    DetailedClassificationReport getDetailedClassificationReport(List<String> workbasketIds, List<TaskState> states,
        List<String> categories, List<String> domains, CustomField customField, List<String> customFieldValues,
        List<ReportLineItemDefinition> reportLineItemDefinitions, boolean inWorkingDays)
        throws InvalidArgumentException;

    /**
     * Returns a {@link Report} grouped by the value of a certain {@link CustomField}. The report contains the total
     * numbers of tasks of the respective custom field as well as the total number of all tasks. The tasks of the report
     * are filtered by workbaskets, states, categories, domains and values of a custom field. If no filter is required,
     * the respective parameter should be null. Tasks with Timestamp DUE = null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids to filter by workbaskets. To omit this filter, use null for this parameter
     * @param states
     *            a list of states to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @return the report
     * @throws InvalidArgumentException
     *             thrown if customField is null
     */
    Report getCustomFieldValueReport(List<String> workbasketIds, List<TaskState> states, List<String> categories,
        List<String> domains, CustomField customField, List<String> customFieldValues) throws InvalidArgumentException;

    /**
     * Returns a {@link Report} grouped by the value of a certain {@link CustomField}. For each value of the custom
     * field the report contains the total number of tasks and the number of tasks of the respective cluster that are
     * specified by the {@link ReportLineItemDefinition}s. By default the age of the tasks is counted in working days.
     * Furthermore the Report contains a sum line that contains the total numbers of the different clusters and the
     * total number of all tasks in this report. The tasks of the report are filtered by workbaskets, states,
     * categories, domains and values of a custom field. If no filter is required, the respective parameter should be
     * null. Tasks with Timestamp DUE = null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids objects to filter by workbaskets. To omit this filter, use null for this
     *            parameter
     * @param states
     *            a list of states objects to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @param reportLineItemDefinitions
     *            a list of reportLineItemDefinitions that specify the subdivision into different cluster of due dates.
     *            Days in past are represented as negative values and days in the future are represented as positive
     *            values. To avoid tasks are counted multiple times or not be listed in the report, these
     *            reportLineItemDefinitions should not overlap and should not have gaps. If the ReportLineDefinition
     *            should represent a single day, lowerLimit and upperLimit have to be equal. The outer cluster of a
     *            report should have open ends. These open ends are represented with Integer.MIN_VALUE and
     *            Integer.MAX_VALUE.
     * @return the report
     * @throws InvalidArgumentException
     *             thrown if customField is null
     */
    Report getCustomFieldValueReport(List<String> workbasketIds, List<TaskState> states, List<String> categories,
        List<String> domains, CustomField customField, List<String> customFieldValues,
        List<ReportLineItemDefinition> reportLineItemDefinitions) throws InvalidArgumentException;

    /**
     * Returns a {@link Report} grouped by the value of a certain {@link CustomField}. For each value of the custom
     * field the report contains the total number of tasks and the number of tasks of the respective cluster that are
     * specified by the {@link ReportLineItemDefinition}s. It can be specified whether the age of the tasks is counted
     * in days or in working days. Furthermore the report contains a sum line that contains the total numbers of the
     * different clusters and the total number of all tasks. The tasks of the report are filtered by workbaskets,
     * states, categories, domains and values of a custom field. If no filter is required, the respective parameter
     * should be null. Tasks with Timestamp DUE = null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids objects to filter by workbaskets. To omit this filter, use null for this
     *            parameter
     * @param states
     *            a list of states objects to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @param reportLineItemDefinitions
     *            a list of reportLineItemDefinitions that specify the subdivision into different cluster of due dates.
     *            Days in past are represented as negative values and days in the future are represented as positive
     *            values. To avoid tasks are counted multiple times or not be listed in the report, these
     *            reportLineItemDefinitions should not overlap and should not have gaps. If the ReportLineDefinition
     *            should represent a single day, lowerLimit and upperLimit have to be equal. The outer cluster of a
     *            report should have open ends. These open ends are represented with Integer.MIN_VALUE and
     *            Integer.MAX_VALUE.
     * @param inWorkingDays
     *            a boolean parameter that specifies whether the age of the tasks should be counted in days or in
     *            working days
     * @return the report
     * @throws InvalidArgumentException
     *             thrown if customField is null
     */
    Report getCustomFieldValueReport(List<String> workbasketIds, List<TaskState> states, List<String> categories,
        List<String> domains, CustomField customField, List<String> customFieldValues,
        List<ReportLineItemDefinition> reportLineItemDefinitions, boolean inWorkingDays)
        throws InvalidArgumentException;

    /**
     * Returns a list of all task ids in the selected items of a {@link Report}. By default the age of the tasks is
     * counted in working days. The tasks of the report are filtered by workbaskets, states, categories, domains and
     * values of a custom field. If no filter is required, the respective parameter should be null. Tasks with Timestamp
     * DUE = null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids objects to filter by workbaskets. To omit this filter, use null for this
     *            parameter
     * @param states
     *            a list of states objects to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field. To omit this filter, use null for this
     *            parameter
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @param reportLineItemDefinitions
     *            a list of reportLineItemDefinitions that specify the subdivision into different cluster of due dates.
     *            Days in past are represented as negative values and days in the future are represented as positive
     *            values. To avoid tasks are counted multiple times or not be listed in the report, these
     *            reportLineItemDefinitions should not overlap and should not have gaps. If the ReportLineDefinition
     *            should represent a single day, lowerLimit and upperLimit have to be equal. The outer cluster of a
     *            report should have open ends. These open ends are represented with Integer.MIN_VALUE and
     *            Integer.MAX_VALUE.
     * @param selectedItems
     *            a list of {@link SelectedItem}s that are selected from the report whose task ids should be determined.
     * @return the list of task ids
     * @throws InvalidArgumentException
     *             thrown if reportLineItemDefinitions is null or if selectedItems is empty or null
     */
    List<String> getTaskIdsOfCategoryReportLineItems(List<String> workbasketIds, List<TaskState> states,
        List<String> categories, List<String> domains, CustomField customField, List<String> customFieldValues,
        List<ReportLineItemDefinition> reportLineItemDefinitions, List<SelectedItem> selectedItems)
        throws InvalidArgumentException;

    /**
     * Returns a list of all task ids in the selected items of a {@link Report}. By default the age of the tasks is
     * counted in working days. The tasks of the report are filtered by workbaskets, states, categories, domains and
     * values of a custom field. If no filter is required, the respective parameter should be null. Tasks with Timestamp
     * DUE = null are not considered.
     *
     * @param workbasketIds
     *            a list of workbasket ids objects to filter by workbaskets. To omit this filter, use null for this
     *            parameter
     * @param states
     *            a list of states objects to filter by states. To omit this filter, use null for this parameter
     * @param categories
     *            a list of categories to filter by categories. To omit this filter, use null for this parameter
     * @param domains
     *            a list of domains to filter by domains. To omit this filter, use null for this parameter
     * @param customField
     *            a custom field to filter by the values of the custom field. To omit this filter, use null for this
     *            parameter
     * @param customFieldValues
     *            a list of custom field values to filter by the values of the custom field. To omit this filter, use
     *            null for this parameter
     * @param reportLineItemDefinitions
     *            a list of reportLineItemDefinitions that specify the subdivision into different cluster of due dates.
     *            Days in past are represented as negative values and days in the future are represented as positive
     *            values. To avoid tasks are counted multiple times or not be listed in the report, these
     *            reportLineItemDefinitions should not overlap and should not have gaps. If the ReportLineDefinition
     *            should represent a single day, lowerLimit and upperLimit have to be equal. The outer cluster of a
     *            report should have open ends. These open ends are represented with Integer.MIN_VALUE and
     *            Integer.MAX_VALUE.
     * @param inWorkingDays
     *            a boolean parameter that specifies whether the age of the tasks should be counted in days or in
     *            working days
     * @param selectedItems
     *            a list of {@link SelectedItem}s that are selected from the report whose task ids should be determined.
     * @return the list of task ids
     * @throws InvalidArgumentException
     *             thrown if reportLineItemDefinitions is null or if selectedItems is empty or null
     */
    List<String> getTaskIdsOfCategoryReportLineItems(List<String> workbasketIds, List<TaskState> states,
        List<String> categories, List<String> domains, CustomField customField, List<String> customFieldValues,
        List<ReportLineItemDefinition> reportLineItemDefinitions, boolean inWorkingDays,
        List<SelectedItem> selectedItems) throws InvalidArgumentException;

}
