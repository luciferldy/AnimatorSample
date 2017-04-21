# Animator Sample



Android animation sample contains `animation` , `animator` , `fragment transition` , `activity transition` 

Now I strongly recommend that you should read [https://github.com/lgvalle/Material-Animations/](https://github.com/lgvalle/Material-Animations/). That article introduce the usage of android transition framework. You will get the point of enter, return, exit, reenter, etc.

### Animation

Use `xml` or `Animation` subclass to implement effect rotate, transition, alpha and scale.

You can check code in `BaseAnimationActivity.class` .

![animation](art/animation.gif)

### Animator

Also use `xml` or `ObjectAnimator` class to implement effect rotate, transition, alpha and scale.

![animator](art/animator.gif)

### Fragment Transition

Use `transaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_enter, R.anim.slide_exit);` to implement sliding effect.

**note:** invoke `transition.setCustomAnimation()` before `transition.add()` method, otherwise the transition will not perform sliding effect.

![fragment transition](art/fragment-transition.gif)

### Activity Transition

 Use `AcitivityOptionsCompact.makeSceneTransitionAnimation` to implement `fade` , `explode` , `slide` effect.

**note:** invoke `getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);` before `super.onCreate()` in both src activity and dst activity.

![activity transition](art/activity-transition.gif)

You can also check `SharedElements` in `SharedElementsActivity.class` .

![shared elements](art/shared-elements.gif)

Some other reference:

[https://github.com/yipianfengye/Android-activityAnim](https://github.com/yipianfengye/Android-activityAnim)

