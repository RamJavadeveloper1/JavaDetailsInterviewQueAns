Here are **20 Angular interview questions with concise answers**, covering beginner to advanced topics:

## 1. What is Angular?

Angular is a TypeScript-based front-end framework developed by Google for building single-page applications (SPAs). It uses components, services, dependency injection, and RxJS.

---

## 2. What are the key features of Angular?

* Component-based architecture
* Dependency Injection (DI)
* Two-way data binding
* Routing
* Directives
* Pipes
* Reactive Forms
* RxJS Observables
* Lazy Loading

---

## 3. What is a Component?

A component is the basic building block of an Angular application. It consists of:

* HTML Template
* TypeScript Class
* CSS Styles

Example:

```typescript
@Component({
  selector: 'app-user',
  template: `<h1>{{name}}</h1>`
})
export class UserComponent {
  name = 'John';
}
```

---

## 4. What is Data Binding in Angular?

Data binding synchronizes data between component and view.

Types:

* Interpolation: `{{data}}`
* Property Binding: `[src]="imageUrl"`
* Event Binding: `(click)="save()"`
* Two-way Binding: `[(ngModel)]="name"`

---

## 5. What is the difference between Property Binding and Interpolation?

| Interpolation      | Property Binding          |
| ------------------ | ------------------------- |
| `{{name}}`         | `[value]="name"`          |
| Works with strings | Works with DOM properties |
| Display data       | Set element properties    |

---

## 6. What are Directives?

Directives modify DOM behavior.

Types:

1. Component Directives
2. Structural Directives (`*ngIf`, `*ngFor`)
3. Attribute Directives (`ngClass`, `ngStyle`)

Example:

```html
<div *ngIf="isLoggedIn">
  Welcome
</div>
```

---

## 7. Difference between *ngIf and [hidden]

`*ngIf`

```html
<div *ngIf="show"></div>
```

* Removes element from DOM.

`[hidden]`

```html
<div [hidden]="!show"></div>
```

* Hides element using CSS.
* Element remains in DOM.

---

## 8. What is Dependency Injection (DI)?

DI is a design pattern where Angular automatically provides required dependencies.

Example:

```typescript
constructor(private userService: UserService) {}
```

Benefits:

* Loose coupling
* Better testing
* Reusability

---

## 9. What are Services?

Services contain business logic and reusable code.

Example:

```typescript
@Injectable()
export class UserService {
  getUsers() {
    return this.http.get('/api/users');
  }
}
```

---

## 10. What is Angular Lifecycle?

Important lifecycle hooks:

```typescript
ngOnInit()
ngOnChanges()
ngDoCheck()
ngAfterViewInit()
ngOnDestroy()
```

Most commonly used:

```typescript
ngOnInit() {
  this.loadData();
}
```

---

## 11. What is Change Detection?

Angular updates the DOM when component data changes.

Strategies:

```typescript
ChangeDetectionStrategy.Default
ChangeDetectionStrategy.OnPush
```

`OnPush` improves performance by checking only when input references change.

---

## 12. What is Lazy Loading?

Loads modules only when needed.

```typescript
{
 path: 'admin',
 loadChildren: () =>
   import('./admin/admin.module')
   .then(m => m.AdminModule)
}
```

Benefits:

* Faster initial load

* Reduced bundle size

---

## 13. What are Observables?

Observables handle asynchronous data streams using RxJS.

```typescript
this.http.get('/users')
  .subscribe(data => console.log(data));
```

---

## 14. Observable vs Promise

| Observable      | Promise               |
| --------------- | --------------------- |
| Multiple values | Single value          |
| Cancelable      | Not cancelable        |
| Lazy            | Eager                 |
| RxJS operators  | Limited functionality |

---

## 15. What is Subject in RxJS?

A Subject acts as both:

* Observable
* Observer

```typescript
const subject = new Subject();

subject.subscribe(data => console.log(data));

subject.next('Hello');
```

Types:

* Subject
* BehaviorSubject
* ReplaySubject
* AsyncSubject

---

## 16. Difference between Reactive Forms and Template-driven Forms

| Reactive Forms    | Template Forms   |
| ----------------- | ---------------- |
| Code-driven       | Template-driven  |
| More scalable     | Simpler          |
| Better validation | Basic validation |
| Easy testing      | Harder testing   |

Example:

```typescript
this.form = this.fb.group({
  name: ['', Validators.required]
});
```

---

## 17. What is Route Guard?

Controls navigation.

Types:

* CanActivate
* CanDeactivate
* Resolve
* CanLoad
* CanMatch

Example:

```typescript
canActivate(): boolean {
  return this.authService.isLoggedIn();
}
```

---

## 18. What is ViewChild?

Used to access child components or DOM elements.

```typescript
@ViewChild('inputRef')
input!: ElementRef;

ngAfterViewInit() {
  this.input.nativeElement.focus();
}
```

---

## 19. What is the difference between Constructor and ngOnInit?

| Constructor          | ngOnInit                        |
| -------------------- | ------------------------------- |
| Dependency injection | Component initialization        |
| Called first         | Called after component creation |
| Avoid API calls      | Suitable for API calls          |

---

## 20. What is Ahead-of-Time (AOT) Compilation?

Angular compiles templates during build time.

Benefits:

* Faster rendering
* Smaller bundles
* Better security
* Early error detection

---

### Frequently Asked Angular Interview Coding Questions

1. Create a custom pipe.
2. Build a reusable shared component.
3. Implement parent-child communication using:

   * `@Input()`
   * `@Output()`
4. Create Reactive Form validation.
5. Implement HTTP interceptor.
6. Create Route Guard.
7. Use RxJS operators (`map`, `switchMap`, `mergeMap`, `debounceTime`).
8. Implement Lazy Loading.

### Angular 17+ Interview Questions

* What are Standalone Components?
* What are Signals?
* Difference between Signals and Observables?
* What is `@if`, `@for`, and `@switch` control flow syntax?
* How does Server-Side Rendering (SSR) work in Angular?
* What are Deferrable Views (`@defer`)?

These Angular 17+ topics are very commonly asked in recent interviews.


Since you are a **Senior Java Developer with 6+ years of experience**, Angular interviews will usually focus on **practical concepts, RxJS, API integration, forms, routing, performance, and Angular 17 features**. Here are detailed answers:

------------------------------------------------------------------------------------------------------------------------------------------

# 1. What is Angular?

Angular is a TypeScript-based frontend framework developed by Google for building Single Page Applications (SPAs).

Features:

* Component-based architecture
* Dependency Injection
* Routing
* Reactive Forms
* RxJS
* Lazy Loading

Example:

```typescript
@Component({
 selector: 'app-root',
 template: '<h1>Hello Angular</h1>'
})
export class AppComponent {}
```

---

# 2. Why Angular instead of JavaScript?

Advantages:

* Structured architecture
* Reusable components
* Built-in routing
* Dependency Injection
* Form validation
* TypeScript support
* Better maintainability

---

# 3. What is TypeScript?

TypeScript is a superset of JavaScript that adds:

* Types
* Interfaces
* Classes
* Decorators

Example:

```typescript
let name: string = "Ram";
let age: number = 30;
```

Benefits:

* Compile-time checking
* Better IDE support
* Easier maintenance

---

# 4. What is a Component?

A component controls a portion of UI.

Example:

```typescript
@Component({
 selector:'app-user',
 template:'<h1>{{name}}</h1>'
})
export class UserComponent{
 name='Ram';
}
```

A component consists of:

* HTML
* CSS
* TypeScript

---

# 5. What is a Module?

Module groups related functionality.

Example:

```typescript
@NgModule({
 declarations:[UserComponent],
 imports:[BrowserModule]
})
export class AppModule{}
```

Common modules:

* AppModule
* SharedModule
* FeatureModule

---

# 6. What is Data Binding?

Data binding connects component and UI.

## Interpolation

```html
{{name}}
```

## Property Binding

```html
<img [src]="imageUrl">
```

## Event Binding

```html
<button (click)="save()">
```

## Two-way Binding

```html
<input [(ngModel)]="name">
```

---

# 7. Explain Two-Way Binding

Synchronizes data between view and component.

```html
<input [(ngModel)]="username">
```

Equivalent:

```html
<input
 [ngModel]="username"
 (ngModelChange)="username=$event">
```

---

# 8. What are Directives?

Directives change DOM behavior.

### Structural Directive

```html
<div *ngIf="show"></div>
```

```html
<li *ngFor="let user of users"></li>
```

### Attribute Directive

```html
<div [ngClass]="{'active':true}">
```

---

# 9. Difference between ngIf and hidden

### ngIf

```html
<div *ngIf="show"></div>
```

Removes element from DOM.

### hidden

```html
<div [hidden]="!show"></div>
```

Only hides element.

Interview Answer:

> ngIf destroys and recreates components while hidden keeps them in DOM.

---

# 10. What is Dependency Injection?

Angular automatically provides required objects.

```typescript
constructor(
 private userService: UserService
){}
```

Benefits:

* Loose coupling
* Easy testing
* Reusability

---

# 11. What is a Service?

Service contains business logic.

```typescript
@Injectable()
export class UserService {

 getUsers() {
   return this.http.get('/users');
 }

}
```

---

# 12. Explain Lifecycle Hooks

## ngOnInit

```typescript
ngOnInit() {
 this.loadUsers();
}
```

## ngOnChanges

Runs when Input changes.

## ngAfterViewInit

Runs after view loads.

## ngOnDestroy

Cleanup subscriptions.

```typescript
ngOnDestroy() {
 this.subscription.unsubscribe();
}
```

---

# 13. Constructor vs ngOnInit

Constructor:

```typescript
constructor(private service: UserService){}
```

Used for:

* Dependency injection

ngOnInit:

```typescript
ngOnInit(){
 this.loadData();
}
```

Used for:

* API calls
* Initialization

---

# 14. What are Observables?

RxJS Observable handles async data.

```typescript
this.http.get('/users')
.subscribe(users=>{
 console.log(users);
});
```

Examples:

* HTTP calls
* WebSocket
* Events

---

# 15. Observable vs Promise

Observable:

```typescript
this.http.get('/users')
```

Promise:

```typescript
fetch(url)
.then()
```

Difference:

| Observable      | Promise      |
| --------------- | ------------ |
| Multiple values | Single value |
| Cancelable      | No           |
| Lazy            | Eager        |
| RxJS operators  | No           |

---

# 16. What is Subject?

Acts as both:

* Observer
* Observable

```typescript
const subject = new Subject();

subject.subscribe(x=>console.log(x));

subject.next("Hello");
```

---

# 17. BehaviorSubject vs Subject

Subject:

```typescript
subject.next("A");
```

New subscriber won't get old value.

BehaviorSubject:

```typescript
new BehaviorSubject("Initial");
```

New subscriber gets latest value.

Used for:

* Logged-in user
* Shared state

---

# 18. What is switchMap?

Cancels previous request.

Search example:

```typescript
search$
.pipe(
 debounceTime(300),
 switchMap(value =>
   this.http.get('/search')
 )
)
```

Best for:

* Search APIs

---

# 19. What is mergeMap?

Runs all requests simultaneously.

```typescript
mergeMap(id =>
 this.http.get(`/user/${id}`)
)
```

No cancellation.

---

# 20. What is debounceTime?

Waits before emitting value.

```typescript
debounceTime(500)
```

Used in:

```typescript
Search Box
```

to reduce API calls.

---

# 21. What is Reactive Form?

Code-driven form.

```typescript
this.form = this.fb.group({
 name:['']
});
```

HTML:

```html
<form [formGroup]="form">
```

Advantages:

* Dynamic forms
* Easier testing
* Better validation

---

# 22. Custom Validation Example

```typescript
ageValidator(control:any){

 if(control.value < 18){
   return {invalidAge:true};
 }

 return null;
}
```

---

# 23. What is Routing?

Navigation between pages.

```typescript
const routes=[
 {path:'users',component:UserComponent}
];
```

---

# 24. What is Lazy Loading?

Loads module only when required.

```typescript
{
 path:'admin',
 loadChildren:() =>
 import('./admin/admin.module')
 .then(m=>m.AdminModule)
}
```

Benefits:

* Faster startup
* Smaller bundle

---

# 25. What is Route Guard?

Protect routes.

```typescript
canActivate():boolean{
 return this.auth.isLoggedIn();
}
```

Types:

* CanActivate
* CanDeactivate
* Resolve
* CanLoad

---

# 26. What is HTTP Interceptor?

Intercepts requests/responses.

JWT Example:

```typescript
intercept(req,next){

 const tokenReq=req.clone({
   setHeaders:{
     Authorization:`Bearer ${token}`
   }
 });

 return next.handle(tokenReq);
}
```

---

# 27. Explain Change Detection

Angular checks component data changes and updates DOM.

Strategies:

```typescript
Default
```

```typescript
OnPush
```

For performance:

```typescript
changeDetection:
ChangeDetectionStrategy.OnPush
```

---

# 28. What is ViewChild?

Access child component or DOM.

```typescript
@ViewChild('name')
name!: ElementRef;
```

```typescript
ngAfterViewInit(){
 this.name.nativeElement.focus();
}
```

---

# 29. What is Standalone Component? (Angular 17)

No NgModule required.

```typescript
@Component({
 standalone:true,
 imports:[CommonModule]
})
export class UserComponent{}
```

Benefits:

* Less boilerplate
* Faster development

---

# 30. What are Signals? (Angular 17)

New reactive state management.

```typescript
count = signal(0);

count.set(1);
```

Read:

```typescript
count()
```

Update:

```typescript
count.update(v=>v+1);
```

Benefits:

* Faster than traditional change detection
* Simpler state management

---

# 31. Signals vs Observable

| Signal           | Observable    |
| ---------------- | ------------- |
| State management | Async streams |
| Synchronous      | Async         |
| Simpler          | More powerful |
| Angular feature  | RxJS feature  |

---

# 32. What is @if? (Angular 17)

Old:

```html
<div *ngIf="show">
```

New:

```html
@if(show){
 <div>Hello</div>
}
```

---

# 33. What is @for? (Angular 17)

Old:

```html
<li *ngFor="let user of users">
```

New:

```html
@for(user of users; track user.id){
 <li>{{user.name}}</li>
}
```

---

# 34. What is SSR?

Server Side Rendering.

Benefits:

* Faster page load
* Better SEO
* Better performance

Implemented using:

Angular Universal

---

# 35. Angular Interview Question Often Asked to Experienced Developers

### How do Parent and Child Components Communicate?

Parent to Child:

```typescript
@Input()
user!: User;
```

Child to Parent:

```typescript
@Output()
save = new EventEmitter<User>();
```

---

### How do you avoid memory leaks?

```typescript
private destroy$ = new Subject<void>();

this.service.getData()
.pipe(
 takeUntil(this.destroy$)
)
.subscribe();
```

```typescript
ngOnDestroy(){
 this.destroy$.next();
 this.destroy$.complete();
}
```

---

### Difference Between map, switchMap, mergeMap, concatMap?

| Operator  | Behavior             |
| --------- | -------------------- |
| map       | Transform value      |
| switchMap | Cancel previous      |
| mergeMap  | Parallel execution   |
| concatMap | Sequential execution |

These RxJS operators, Reactive Forms, Interceptors, Guards, Lazy Loading, Signals, and Angular 17 control flow features are among the most common questions in Angular interviews for developers with 3–8 years of experience.

------------------------------------------------------------------------------------------------------------------------------------------


These are very common **Angular 17+ interview questions**.

# 1. What are Standalone Components?

Standalone Components allow you to create Angular components **without NgModule**.

Before Angular 14:

```typescript
@NgModule({
  declarations: [UserComponent],
  imports: [CommonModule]
})
export class UserModule {}
```

Angular 17:

```typescript
@Component({
  selector: 'app-user',
  standalone: true,
  imports: [CommonModule],
  template: `<h1>User</h1>`
})
export class UserComponent {}
```

### Usage

```typescript
{
  path: 'user',
  loadComponent: () =>
    import('./user.component')
      .then(m => m.UserComponent)
}
```

### Advantages

✅ Less boilerplate

✅ Better lazy loading

✅ Faster application startup

✅ Easier maintenance

### Interview Answer

> Standalone Components are Angular components that do not require an NgModule. They can directly import dependencies and improve lazy loading, readability, and application performance.

---

# 2. What are Signals?

Signals are Angular's built-in reactive state management mechanism introduced in Angular 16.

A Signal stores a value and automatically notifies Angular when the value changes.

Example:

```typescript
import { signal } from '@angular/core';

count = signal(0);
```

Read value:

```typescript
console.log(count());
```

Update value:

```typescript
count.set(5);
```

Increment:

```typescript
count.update(v => v + 1);
```

Template:

```html
<h1>{{ count() }}</h1>
```

### Benefits

* Fine-grained reactivity
* Better performance
* Less change detection overhead
* Simpler than RxJS for local state

### Interview Answer

> Signals are Angular's reactive primitives used for state management. They automatically track dependencies and update only the affected UI parts when data changes.

---

# 3. Difference Between Signals and Observables

| Signals                       | Observables         |
| ----------------------------- | ------------------- |
| Angular feature               | RxJS feature        |
| State management              | Async data streams  |
| Synchronous                   | Asynchronous        |
| Simpler API                   | More powerful       |
| No subscribe needed           | Requires subscribe  |
| Automatic dependency tracking | Manual subscription |

### Signal Example

```typescript
name = signal('Ram');

name.set('Shyam');
```

### Observable Example

```typescript
this.http.get('/users')
.subscribe(data => {
   console.log(data);
});
```

### When to Use Signals

* Component state
* UI state
* Counters
* Forms state

### When to Use Observables

* HTTP calls
* WebSockets
* Event streams
* Complex async operations

### Interview Answer

> Signals are best for local reactive state, while Observables are designed for asynchronous streams such as HTTP requests and real-time events.

---

# 4. What is @if?

Angular 17 introduced new control flow syntax.

Old:

```html
<div *ngIf="isLoggedIn">
  Welcome
</div>
```

New:

```html
@if(isLoggedIn){
  <div>Welcome</div>
}
```

Else:

```html
@if(isLoggedIn){
  <div>Welcome</div>
} @else {
  <div>Please Login</div>
}
```

### Benefits

* Better readability
* Faster rendering
* More JavaScript-like syntax

---

# 5. What is @for?

Replacement for `*ngFor`.

Old:

```html
<li *ngFor="let user of users">
  {{user.name}}
</li>
```

New:

```html
@for(user of users; track user.id){
  <li>{{user.name}}</li>
}
```

### Why track?

```html
@for(user of users; track user.id)
```

Angular can efficiently update only changed rows.

### Interview Answer

> @for is Angular 17's new loop syntax that replaces *ngFor and provides better performance with built-in tracking.

---

# 6. What is @switch?

Replacement for `ngSwitch`.

Old:

```html
<div [ngSwitch]="role">

  <div *ngSwitchCase="'ADMIN'">
      Admin
  </div>

  <div *ngSwitchDefault>
      User
  </div>

</div>
```

New:

```html
@switch(role){

  @case('ADMIN'){
    <div>Admin</div>
  }

  @case('USER'){
    <div>User</div>
  }

  @default{
    <div>Guest</div>
  }

}
```

### Interview Answer

> @switch is Angular 17's control flow syntax for conditional branching and replaces ngSwitch with cleaner, more readable code.

---

# 7. How Does Server-Side Rendering (SSR) Work?

Normally Angular renders pages in the browser.

Flow:

```text
Browser
   ↓
Downloads JS
   ↓
Angular Bootstraps
   ↓
Page Appears
```

Problem:

* Blank page initially
* Poor SEO

---

### SSR Flow

```text
Browser
    ↓
Server renders HTML
    ↓
HTML sent to browser
    ↓
Browser displays page immediately
    ↓
Angular hydrates page
```

### Benefits

✅ Faster First Contentful Paint

✅ Better SEO

✅ Better social sharing previews

✅ Improved performance on slow devices

### Angular SSR

Angular SSR is provided through the Angular SSR framework (formerly known as Angular Universal).

Example setup:

```bash
ng add @angular/ssr
```

### Interview Answer

> SSR renders Angular pages on the server and sends ready-made HTML to the browser, improving SEO, page-load speed, and user experience.

---

# 8. What is Hydration?

Hydration connects server-rendered HTML with Angular on the client side.

Process:

```text
Server HTML
      ↓
Browser Displays
      ↓
Angular Attaches Events
      ↓
Interactive Application
```

Without hydration:

```text
Server HTML
      ↓
Destroy
      ↓
Re-render Entire Page
```

Hydration improves performance significantly.

---

# 9. What are Deferrable Views (@defer)?

Angular 17 introduced Deferrable Views to lazy-load template content.

Instead of loading everything immediately:

```html
<app-chart></app-chart>
```

Use:

```html
@defer {
  <app-chart />
}
```

Angular loads the chart only when needed.

---

### Loading State

```html
@defer {

  <app-chart />

} @loading {

  <p>Loading...</p>

}
```

---

### Placeholder

```html
@defer {

  <app-chart />

} @placeholder {

  <p>Chart will load later</p>

}
```

---

### Error State

```html
@defer {

  <app-chart />

} @error {

  <p>Failed to load</p>

}
```

---

### Trigger Examples

Load on viewport:

```html
@defer(on viewport) {
  <app-chart />
}
```

Load on hover:

```html
@defer(on hover) {
  <app-chart />
}
```

Load on interaction:

```html
@defer(on interaction) {
  <app-chart />
}
```

Load after timer:

```html
@defer(on timer(3s)) {
  <app-chart />
}
```

---

### Benefits of @defer

✅ Smaller initial bundle

✅ Faster page load

✅ Better Core Web Vitals

✅ Improved user experience

### Interview Answer

> Deferrable Views (@defer) allow Angular to lazy-load components, templates, and dependencies only when needed, reducing the initial bundle size and improving application performance.

---

### One-Line Interview Summary

* **Standalone Components** → Components without NgModule.
* **Signals** → Angular reactive state management.
* **Signals vs Observables** → Signals for state, Observables for async streams.
* **@if/@for/@switch** → New Angular 17 control-flow syntax replacing structural directives.
* **SSR** → Server renders HTML before sending it to the browser.
* **Hydration** → Makes SSR pages interactive on the client.
* **@defer** → Lazy-load UI blocks and components for better performance.


------------------------------------------------------------------------------------------------------------------------------------------
# Anguler tricky question

For **Angular interviews (3–8 years experience)**, interviewers often ask tricky questions to check whether you understand Angular deeply rather than just memorizing definitions.

## 1. Constructor vs ngOnInit

**Question:** Where should you make API calls?

**Answer:**

* Constructor → Dependency Injection only
* ngOnInit → API calls and initialization

```typescript id="8g9ukv"
constructor(private userService: UserService) {}

ngOnInit() {
  this.loadUsers();
}
```

**Trick:** Many candidates say constructor.

---

## 2. ngOnChanges vs ngOnInit

**Question:** Which runs first?

**Answer:**

```text
Constructor
↓
ngOnChanges
↓
ngOnInit
```

If component has `@Input()`, ngOnChanges executes before ngOnInit.

---

## 3. Subject vs BehaviorSubject

**Question:** Which one should store logged-in user information?

**Answer:**
BehaviorSubject

```typescript id="y98ff7"
currentUser = new BehaviorSubject<User | null>(null);
```

Reason:
New subscribers receive the latest value immediately.

---

## 4. switchMap vs mergeMap

**Question:** Which should be used in search functionality?

**Answer:**
switchMap

```typescript id="fzw70e"
search$
.pipe(
  switchMap(text => this.api.search(text))
)
```

Reason:
Cancels previous requests.

**Trick:** mergeMap may return outdated results.

---

## 5. Observable vs Promise

**Question:** Angular HTTP returns Observable. Why not Promise?

**Answer:**
Observables:

* Multiple values
* Cancellation support
* Operators
* Lazy execution

Promises:

* Single value
* No cancellation

---

## 6. Why use trackBy in ngFor?

**Question:** What happens without trackBy?

Without:

```html id="4sbyyh"
<li *ngFor="let user of users">
```

Angular recreates all DOM elements.

With:

```typescript id="i7n6l4"
trackById(index:number,user:any){
 return user.id;
}
```

```html id="n7vfkz"
<li *ngFor="let user of users; trackBy:trackById">
```

Only changed rows are updated.

---

## 7. Why is OnPush Faster?

**Question:** Explain OnPush.

```typescript id="vdggom"
changeDetection:
ChangeDetectionStrategy.OnPush
```

Default:
Checks every component.

OnPush:
Checks only when:

* Input reference changes
* Event occurs
* Observable emits

---

## 8. Why Doesn't Angular Detect This Change?

```typescript id="sy4z7v"
this.user.name = "Ram";
```

With OnPush, UI may not update.

Correct:

```typescript id="rjx7gi"
this.user = {
 ...this.user,
 name: "Ram"
};
```

Reason:
Reference changed.

---

## 9. What Happens If You Don't Unsubscribe?

```typescript id="9r9w8d"
this.service.getData()
.subscribe();
```

Answer:
Memory leak.

Fix:

```typescript id="l3xazt"
takeUntil(this.destroy$)
```

or

```typescript id="y9gl40"
async pipe
```

---

## 10. Async Pipe vs Subscribe

**Question:** Which is better?

```html id="rfh9o0"
{{ users$ | async }}
```

Benefits:

* Auto unsubscribe
* Cleaner code
* Prevents memory leaks

---

## 11. Can Angular Work Without Zone.js?

**Answer:**
Yes.

Angular 17+ supports zoneless applications, especially with Signals.

This is a modern Angular question.

---

## 12. Why Use Reactive Forms Instead of Template Forms?

Answer:

* Dynamic forms
* Better testing
* Better validation
* Complex enterprise applications

Most real projects use Reactive Forms.

---

## 13. What is Difference Between setValue and patchValue?

### setValue

```typescript id="xv4prk"
form.setValue({
 name:'Ram',
 age:30
});
```

Must provide all fields.

### patchValue

```typescript id="a7qfpx"
form.patchValue({
 name:'Ram'
});
```

Partial update allowed.

Very common interview question.

---

## 14. Why Does This Not Work?

```html id="69w56m"
<div>{{ user.address.city }}</div>
```

Error:

```text
Cannot read properties of undefined
```

Fix:

```html id="7nwlcb"
<div>{{ user?.address?.city }}</div>
```

Safe Navigation Operator.

---

## 15. Pure Pipe vs Impure Pipe

Pure:

```typescript id="oj1mhj"
@Pipe({
 pure:true
})
```

Runs only when input changes.

Impure:

```typescript id="m8rvw5"
@Pipe({
 pure:false
})
```

Runs every change detection cycle.

Impure pipes can hurt performance.

---

## 16. Why Is Angular Faster Than Before?

Answer:

* Ivy Renderer
* Standalone Components
* Signals
* Hydration
* Deferrable Views
* Better tree shaking

---

## 17. Why Is This API Not Executing?

```typescript id="jlwm5f"
this.http.get('/users');
```

Answer:
Observable is lazy.

Need:

```typescript id="wsdy1h"
this.http.get('/users')
.subscribe();
```

Very common trick question.

---

## 18. What is the Difference Between ViewChild and ContentChild?

### ViewChild

Access component's own template.

```typescript id="f2nv7g"
@ViewChild('input')
```

### ContentChild

Access projected content.

```html id="4a6vrp"
<app-card>
   <h1>Hello</h1>
</app-card>
```

```typescript id="tvgl0g"
@ContentChild()
```

---

## 19. What Happens If a Service Is Provided in Root?

```typescript id="7yaf4g"
@Injectable({
 providedIn:'root'
})
```

Answer:
Singleton object for entire application.

Only one instance is created.

---

## 20. Can We Inject One Service Into Another?

Yes.

```typescript id="23n3b8"
@Injectable()
export class UserService {

 constructor(
   private authService: AuthService
 ) {}

}
```

---

## Angular 17/18 Advanced Tricky Questions

### Why use Signals if Observables already exist?

Expected answer:

> Signals solve UI state management efficiently with fine-grained reactivity, while Observables remain the preferred solution for asynchronous streams such as HTTP requests and WebSockets.

---

### When should you NOT use Signals?

Answer:

* HTTP APIs
* WebSockets
* Event streams
* Complex RxJS pipelines

Use Observables there.

---

### Is @for faster than *ngFor?

Answer:
Yes.

Because:

* Built-in tracking support
* Better compilation
* Less runtime overhead

---

### Difference Between SSR and Hydration?

SSR:
Server generates HTML.

Hydration:
Browser attaches Angular logic to existing HTML without rebuilding it.

---

These are the kinds of questions that often appear in interviews at companies like Infosys, Tata Consultancy Services, Wipro, Accenture, and many product-based companies when interviewing Angular developers with several years of experience.

------------------------------------------------------------------------------------------------------------------------------------------
Most common asked Java Script quetion in Anguler Interview

In Angular interviews, especially for **3–8 years experienced developers**, interviewers often ask **JavaScript fundamentals first**, because Angular is built on JavaScript and TypeScript.

## Top JavaScript Questions Asked in Angular Interviews

### 1. var vs let vs const

```javascript
var a = 10;
let b = 20;
const c = 30;
```

| var             | let              | const            |
| --------------- | ---------------- | ---------------- |
| Function scoped | Block scoped     | Block scoped     |
| Can redeclare   | Cannot redeclare | Cannot redeclare |
| Can reassign    | Can reassign     | Cannot reassign  |

**Interview Follow-up:**

```javascript
for(var i=0;i<3;i++){}

console.log(i);
```

Output:

```javascript
3
```

---

### 2. == vs ===

```javascript
1 == "1"
```

Output:

```javascript
true
```

Because type conversion occurs.

```javascript
1 === "1"
```

Output:

```javascript
false
```

Because type and value both must match.

---

### 3. What is Hoisting?
Hoisting is JavaScript's behavior of moving variable and function declarations to the top of their scope before execution, so var declarations exist as undefined before assignment. Function declarations are fully hoisted, while let/const are hoisted but remain inaccessible until their declaration.

```javascript
console.log(a);

var a = 10;
```

Output:

```javascript
undefined
```

JavaScript internally treats it as:

```javascript
var a;

console.log(a);

a = 10;
```

---

### 4. What is Closure?

Very common question.

```javascript
function outer(){

 let count = 0;

 return function(){
   count++;
   return count;
 }

}

const counter = outer();

console.log(counter()); //1
console.log(counter()); //2
```

A closure remembers variables from its outer scope even after the outer function has finished execution.

---

### 5. What is Event Loop?

```javascript
console.log("A");

setTimeout(()=>{
 console.log("B");
},0);

console.log("C");
```

Output:

```javascript
A
C
B
```

Reason:

* Call Stack executes first.
* Callback Queue waits.
* Event Loop moves callback when stack becomes empty.

---

### 6. setTimeout(fn,0) Output?

```javascript
console.log(1);

setTimeout(()=>{
 console.log(2);
},0);

console.log(3);
```

Output:

```javascript
1
3
2
```

Very frequently asked.

---

### 7. What is Promise?

```javascript
const promise = new Promise((resolve,reject)=>{

 resolve("Success");

});
```

States:

* Pending
* Fulfilled
* Rejected

---

### 8. Promise vs Observable

Angular-specific favorite question.

Promise:

```javascript
fetch(url)
```

Observable:

```typescript
this.http.get(url)
```

Observable advantages:

* Multiple values
* Cancellation
* RxJS operators

---

### 9. Arrow Function vs Normal Function

```javascript
const add = (a,b)=>a+b;
```

Normal:

```javascript
function add(a,b){
 return a+b;
}
```

Main difference:

**this binding**

Arrow functions don't create their own `this`.

---

### 10. Explain this Keyword

```javascript
const user = {

 name:"Ram",

 print(){
   console.log(this.name);
 }

}
```

Output:

```javascript
Ram
```

`this` refers to the object calling the method.

---

### 11. What is Call, Apply, Bind?

```javascript
function greet(city){
 console.log(this.name,city);
}
```

Call:

```javascript
greet.call({name:"Ram"},"Bhopal");
```

Apply:

```javascript
greet.apply({name:"Ram"},["Bhopal"]);
```

Bind:

```javascript
const fn =
 greet.bind({name:"Ram"});
```

---

### 12. What is Debouncing?

Used heavily in Angular search boxes.

```javascript
search input
```

Without debouncing:

```text
R
Ra
Ram
```

3 API calls.

With debounce(300ms):

```text
Ram
```

1 API call.

---

### 13. What is Throttling?

Limits execution frequency.

Example:

* Scroll events
* Resize events

```text
1000 events
↓
10 executions
```

---

### 14. What is Deep Copy vs Shallow Copy?

Shallow:

```javascript
const b = {...a};
```

Nested objects still share reference.

Deep:

```javascript
structuredClone(a);
```

or

```javascript
JSON.parse(
 JSON.stringify(a)
);
```

---

### 15. What is Destructuring?

```javascript
const user = {
 name:"Ram",
 age:30
};

const {name,age} = user;
```

---

### 16. What is Spread Operator?

```javascript
const arr1=[1,2];
const arr2=[...arr1,3];
```

Output:

```javascript
[1,2,3]
```

---

### 17. What is Rest Operator?

```javascript
function sum(...numbers){

}
```

Collects multiple values into an array.

---

### 18. What is map()?

```javascript
const nums=[1,2,3];

const result=
 nums.map(x=>x*2);
```

Output:

```javascript
[2,4,6]
```

---

### 19. map vs filter vs reduce

Map:

```javascript
[1,2,3]
→
[2,4,6]
```

Filter:

```javascript
[1,2,3,4]
→
[2,4]
```

Reduce:

```javascript
[1,2,3]
→
6
```

---

### 20. What is Temporal Dead Zone (TDZ)?

```javascript
console.log(a);

let a = 10;
```

Output:

```javascript
ReferenceError
```

Reason:
`let` and `const` are in Temporal Dead Zone until initialization.

---

## Most Asked JavaScript Questions in Angular Interviews (Top 10)

1. `var`, `let`, `const`
2. `==` vs `===`
3. Closure
4. Hoisting
5. Event Loop
6. Promise vs Observable
7. Arrow Function vs Normal Function
8. `this`, call, apply, bind
9. map, filter, reduce
10. Debouncing and Throttling

If you're preparing for Angular interviews, focus especially on **Closures, Event Loop, Promises, Async/Await, this keyword, Hoisting, and Promise vs Observable**, because these are asked in almost every Angular interview.
