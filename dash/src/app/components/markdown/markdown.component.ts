import { Component, Input } from '@angular/core';
import { marked } from 'marked';

@Component({
  selector: 'app-markdown',
  templateUrl: './markdown.component.html',
  styleUrls: ['./markdown.component.css']
})
export class MarkdownComponent {
  
  @Input()
  text : string = ""
  
  post_content : string | Promise<string> = ""

  ngOnInit(){
    
    const resEncodedMessage = new TextEncoder().encode(this.text)

    const decoder = new TextDecoder('utf-8');
    const str = decoder.decode(resEncodedMessage);

    this.post_content = marked(str);

  }
}
