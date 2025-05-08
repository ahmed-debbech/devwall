export class Post{
    title : string = "";
    createdAt : string = "";
    id : number = 0;
    tags : string[] = [];
    body : string = "";
    useful : number = 0;
    notUseful : number = 0;

    constructor(title : string, createdAt : string, id : number, body : string){
        this.title = title
        this.createdAt = createdAt
        this.id = id
        this.body = body
    }
}