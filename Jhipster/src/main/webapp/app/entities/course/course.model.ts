import * as dayjs from 'dayjs';

export interface ICourse {
  id?: number;
  name?: string | null;
  type?: string | null;
  starttime?: dayjs.Dayjs | null;
  duration?: string | null;
  intensity?: number | null;
  location?: string | null;
  currentsize?: number | null;
  maxsize?: number | null;
}

export class Course implements ICourse {
  constructor(
    public id?: number,
    public name?: string | null,
    public type?: string | null,
    public starttime?: dayjs.Dayjs | null,
    public duration?: string | null,
    public intensity?: number | null,
    public location?: string | null,
    public currentsize?: number | null,
    public maxsize?: number | null
  ) {}
}

export function getCourseIdentifier(course: ICourse): number | undefined {
  return course.id;
}
