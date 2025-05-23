import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { marked } from 'marked';
import { Post } from 'src/app/model/Post';
import { PostService } from 'src/app/services/post/post.service';
import * as moment from 'moment';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent {

  post !: Post
  mom = moment

  constructor(private postService : PostService, private router: Router){}

  parseInt(s : string) : number{
    return parseInt(s)
  }
  
  ngOnInit(){

    this.postService.getSinglePostByRandomId(this.router.url.split('/')[this.router.url.split('/').length-1]).subscribe((res) => {
      this.post = res
    })

    let text = "**Java's Hidden Gem: Enum vs Class**\n\nIn Java, `enum` is a special type of class that provides a more concise way to define a fixed number of constants. While it may seem similar to a regular class, there are some key differences.\n\n**Why use Enum?**\n\nEnums are useful when you need to represent a fixed set of named values. They provide several benefits over using regular classes:\n\n*   **Immutability**: Enums are implicitly final and cannot be subclassed.\n*   **Compile-time checking**: The Java compiler checks that all enum constants are assigned, ensuring they are all valid values.\n\n**Example Code:**\n\n```java\npublic class Color {\n    public static enum ColorEnum {\n        RED, GREEN, BLUE\n    }\n\n    public static void main(String[] args) {\n        System.out.println(Color.ColorEnum.GREEN); // prints \"GREEN\"\n        Color.ColorEnum color = Color.ColorEnum.RED;\n        if (color == Color.ColorEnum.GREEN) { // false\n            System.out.println(\"Color is green\");\n        }\n    }\n}\n```\n\nIn this example, we define an enum `ColorEnum` with three constants: RED, GREEN, and BLUE. We can then use these constants in our code to represent different colors.\n\n**Note**: In Java 14 and later, you can use the `record` keyword to create a simple enum. Here's how:\n\n```java\npublic record Color(String value) {\n}\n\npublic class Main {\n    public static void main(String[] args) {\n        Color color = new Color(\"RED\");\n        System.out.println(color.value()); // prints \"RED\"\n    }\n}\n```\n\nThis approach is more concise and expressive than traditional enums. However, it's still a good idea to use traditional enums when you need to include methods or fields with the enum."
    this.post = new Post("How to create things", "1746718781", 123456, text)
    
  }

}
