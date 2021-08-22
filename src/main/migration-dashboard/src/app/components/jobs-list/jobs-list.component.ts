import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-jobs-list',
  templateUrl: './jobs-list.component.html',
  styleUrls: ['./jobs-list.component.scss']
})
export class JobsListComponent implements OnInit {

  dataSource: any[] = [
    { title: "Todo migration", actions: [
      { icon: "visibility", url: "/job/todo-job" }
    ] }
  ]

  displayedColumns: string[] = ['title', 'actions'];

  constructor() { }

  ngOnInit(): void {
  }

}
