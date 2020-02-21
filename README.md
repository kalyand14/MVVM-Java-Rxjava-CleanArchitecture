<H1>Todo Application</H1>

  A sample android app that shows how to use ViewModels and Room together with RxJava & Pure Depdency Injection, in Java by Clean Architecture.
  
<b>Implemented by Clean Architecture</b>

Presentation (Activity/Fragment and ViewModel)
Domain (Interactor/Usecases, Repository Interface and Model classes)
Data (Local/remote datastore and Respository implementation)

<b>Dependecy between compoents</b>
 
 Activity/Fragment  --> ViewModel --> Usecase(Interactor) --> Repository --> LocalDataStore(Room DB)

<b>Communication between layers</b>

 Below are few component that i want to highlight.
 
 1) Navigator
 2) ResourceManger

References:
