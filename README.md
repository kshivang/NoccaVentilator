# NoccaVentilator

## Design

[android zeplin](https://zpl.io/258kmwn)

[showcase](https://scene.zeplin.io/project/5e8789e1b5d50bbb08b003b0)

## Code Design

__| Backend | <=> | Rx functional API | <=> | Frontend |__

**Backend** is assunmed to be asyncronous therefore not safe to call directly from UI main thread.

**Rx functional api** will bridge this async bridge with awesome thread management.

### How to create these Rx API
It's quite simple you just have to follow following syntax
```kotlin
let someObservable get() = Observable.create<String> {
    let listener = backendCall( { result ->
        it.onNext(result)
    })
    it.setCancellable { listener.release() } // this may/may not be used
}.apply {
    subscribeOn(Schedulers.io())
    observeOn(AndroidSchedulers.mainThread())
}
```


Since this app backend will implement connection protocal(USB connection), 
therefore, lifecycle aware backend may be required, hence [Single Activity Architecture](https://www.youtube.com/watch?v=2k8x8V77CrU).

### HolderActivity & BaseActivity
HolderActivity extends BaseActivity for better readiblity purposes. 
For view related code use HolderActivity and for backend related code use BaseActivity.
All of HolderActivity life-cycle call is also passed to BaseActivity as well.

#### onCreate
If you want to initialise 3rd party sdk or establish device backend connection do it here

#### onDestroy
Release and clean all of resources like realtime listener or socket connection here.

### BaseFragment
This is abstract fragment which expect `val title` to be implemented
```kotlin
val title: get()
```
which is used as title shown by activity while showing this fragment.
#### onViewCreated
Here you should subscribe to api observable and pass value to handler and update view using runOnActive handler.
e.g.: 
```kotlin
someObservable.subscribe { string ->
  runOnActive {
    someView.text = string
  }
}
```







