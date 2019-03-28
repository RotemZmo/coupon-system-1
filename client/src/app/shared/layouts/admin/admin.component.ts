import {Component} from '@angular/core'
import {Subscription} from 'rxjs'
import {MediaChange, ObservableMedia} from '@angular/flex-layout'
import {AuthorisationService} from "../../services/authorisation.service"
import {DatePipe} from "@angular/common"

@Component({
  selector: 'app-login',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {

  date = new DatePipe('en-US').transform(new Date(), 'EEEE, MMMM d, y')

  opened = true
  over = 'side'
  expandHeight = '42px'
  collapseHeight = '42px'
  displayMode = 'flat'

  watcher: Subscription

  constructor(media: ObservableMedia, public auth: AuthorisationService) {
    this.watcher = media.subscribe((change: MediaChange) => {
      if (change.mqAlias === 'sm' || change.mqAlias === 'xs') {
        this.opened = false
        this.over = 'over'
      } else {
        this.opened = true
        this.over = 'side'
      }
    })
  }
}



