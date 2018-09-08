import {Classification} from 'app/models/classification';
import {Workbasket} from 'app/models/workbasket';

export class Task {
  constructor(public businessProcessId: string,
              public parentBusinessProcessId: string,
              public owner: string,
              public taskId: string,
              public created: string,    // ISO-8601
              public claimed: string,    // ISO-8601
              public completed: string,  // ISO-8601
              public modified: string,   // ISO-8601
              public planned: string,    // ISO-8601
              public due: string,        // ISO-8601
              public name: string,
              public creator: string,
              public description: string,
              public note: string,
              public state: any,
              public read: boolean,
              public transferred: boolean,
              public priority: number,
              public classificationSummaryResource: Classification,
              public workbasketSummaryResource: Workbasket,
              public customAttributes: Object,
              public callbackInfo: Object,
              public custom1: string,
              public custom2: string,
              public custom3: string,
              public custom4: string,
              public custom5: string,
              public custom6: string,
              public custom7: string,
              public custom8: string,
              public custom9: string,
              public custom10: string,
              public custom11: string,
              public custom12: string,
              public custom13: string,
              public custom14: string,
              public custom15: string,
              public custom16: string) {
  }
}

export class CustomAttribute {
  key: string;
  value: string;
}

export function convertToCustomAttributes(callbackInfo: boolean = false): CustomAttribute[] {
  return Object.keys(callbackInfo ? this.callbackInfo : this.customAttributes)
    .map(k => ({ key: k, value: (callbackInfo ? this.callbackInfo : this.customAttributes)[k] }));
}

export function saveCustomAttributes(attributes: CustomAttribute[], callbackInfo: boolean = false): void {
  const att: Object = attributes.filter(attr => attr.key).reduce((acc, obj) => {
    acc[obj.key] = obj.value;
    return acc;
  }, {});
  if (callbackInfo) {
    this.callbackInfo = att;
  } else {
    this.customAttributes = att;
  }
}
