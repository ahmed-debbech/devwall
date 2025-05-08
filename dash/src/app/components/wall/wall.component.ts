import { Component } from '@angular/core';
import { Post } from 'src/app/model/Post';
import * as moment from 'moment';

@Component({
  selector: 'app-wall',
  templateUrl: './wall.component.html',
  styleUrls: ['./wall.component.css']
})
export class WallComponent {

  posts : Post[] = [];
  ngOnInit(){
    this.posts.push({
      title : "how to make things beautiful in java",
      createdAt : moment(1746389748*1000).fromNow(),
      id :  1,
      tags : ["Spring", "Spring boot", "Java17"],
      body :  "The unix time stamp is a way to track time as a running total of seconds. This count starts at the Unix Epoch on January 1st, 1970 at UTC. Therefore, the unix time stamp is merely the number of seconds between a particular date and the Unix Epoch. It should also be pointed out (thanks to the comments from visitors to this site) that this point in time technically does not change no matter where you are located on the globe. This is very useful to computer systems for tracking and sorting dated information in dynamic and distributed applications both online and client side.",
      useful : 5,
      notUseful : 1
    });

    this.posts.push({
      title : "how to make things beautiful in java",
      createdAt : moment(1746389748*1000).fromNow(),
      id :  1,
      tags : ["Spring", "Js", "Java17"],
      body :  "The unix time stamp is a way to track time as a running total of seconds. This count starts at the Unix Epoch on January 1st, 1970 at UTC. Therefore, the unix time stamp is merely the number of seconds between a particular date and the Unix Epoch. It should also be pointed out (thanks to the comments from visitors to this site) that this point in time technically does not change no matter where you are located on the globe. This is very useful to computer systems for tracking and sorting dated information in dynamic and distributed applications both online and client side.",
      useful : 5,
      notUseful : 1
    });

    this.posts.push({
      title : "how to make things beautiful in java",
      createdAt : moment(1746389748*1000).fromNow(),
      id :  1,
      tags : ["Spring", "Spring boot", "Kafka"],
      body :  "The unix time stamp is a way to track time as a running total of seconds. This count starts at the Unix Epoch on January 1st, 1970 at UTC. Therefore, the unix time stamp is merely the number of seconds between a particular date and the Unix Epoch. It should also be pointed out (thanks to the comments from visitors to this site) that this point in time technically does not change no matter where you are located on the globe. This is very useful to computer systems for tracking and sorting dated information in dynamic and distributed applications both online and client side.",
      useful : 5,
      notUseful : 1
    });

    this.posts.push({
      title : "how to make things beautiful in java",
      createdAt : moment(1746389748*1000).fromNow(),
      id :  1,
      tags : ["Angular", "Spring boot", "Java17"],
      body :  "The unix time stamp is a way to track time as a running total of seconds. This count starts at the Unix Epoch on January 1st, 1970 at UTC. Therefore, the unix time stamp is merely the number of seconds between a particular date and the Unix Epoch. It should also be pointed out (thanks to the comments from visitors to this site) that this point in time technically does not change no matter where you are located on the globe. This is very useful to computer systems for tracking and sorting dated information in dynamic and distributed applications both online and client side.",
      useful : 5,
      notUseful : 1
    });
  }
}
