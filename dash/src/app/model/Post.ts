import { PostTag } from "./PostTag";

export class Post{
    title : string = "";
    createdAt : string = "";
    id : number = 0;
    body : string = "";
    useful : number = 0;
    notUseful : number = 0;
    tags : PostTag[] = []

    constructor(title : string, createdAt : string, id : number, body : string){
        this.title = title
        this.createdAt = createdAt
        this.id = id
        this.body = body
    }
}