
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2e5580f459324f4f9352b0a714628e46)](https://app.codacy.com/manual/kalyand14/MVVM-Java-Rxjava-CleanArchitecture?utm_source=github.com&utm_medium=referral&utm_content=kalyand14/MVVM-Java-Rxjava-CleanArchitecture&utm_campaign=Badge_Grade_Dashboard)

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
  <li>ViewModel executes Use case.</li>
  <li>Use case combines data from Album and Photo Repositories.</li>
  <li>Each Repository returns data from a Data Source (Cached or Remote).</li>
  <li>Information flows back to the UI where we display the list of posts.</li>
</ol>
<b>Other important components</b>
<ul>
  <li>Navigator</li>
  <li>ResourceManger</li>
 </ul>
 
<b>Scenario</b>

At a glance:
<ul>
  <li>Use can either Register or Login if already registered</li>
  <li>Once authenticated, show list of todos with option to add new todo</li>
  <li>Once tap on each item, show edit/delete todo screen</li>
 </ul>

<b>References</b>
