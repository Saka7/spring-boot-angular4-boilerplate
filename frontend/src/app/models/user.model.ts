export class User {
  
  constructor(
    public id: number,
    public name: string
  ) {}

  toString() {
    return JSON.stringify(this);
  }

}
