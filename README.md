<H1>Todo Application</H1>

  A sample android app that shows how to use ViewModels and Room together with RxJava & Pure Depdency Injection, in Java by Clean Architecture.
  
<b>Implemented by Clean Architecture</b>

<ul>
<li>Presentation (Activity/Fragment and ViewModel)</li>
<li>Domain (Interactor/Usecases, Repository Interface and Model classes)</li>
<li>Data (Local/remote datastore and Respository implementation)</li>
</ul>

<b>Dependecy between compoents</b>
 
 Activity/Fragment  --> ViewModel --> Usecase(Interactor) --> Repository --> LocalDataStore(Room DB)

<b>Communication between layers</b>
<ol>
  <li>UI calls method from ViewModel.</li>
ViewModel executes Use case.
Use case combines data from Album and Photo Repositories.
Each Repository returns data from a Data Source (Cached or Remote).
Information flows back to the UI where we display the list of posts.
</ol>
<b>Other important components</b>
<ul>
  <li>Navigator</li>
  <li>ResourceManger</li>
 </ul>
 
<b>Scenario</b>

At a glance:

<b>References</b>
