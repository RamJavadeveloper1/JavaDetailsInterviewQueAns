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
