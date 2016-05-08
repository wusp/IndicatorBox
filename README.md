# wuspViewPkg

This package includes some of Android custom views I create, most of them have animation effects. <br />
I'll add my custom views to this package and optimize them continually. <br />
Each of them has a .gif animation to simply display the animation effect and using example. <br />

####Menu
[FlashBorderView](https://github.com/wusp/WuspViewPkg#flashborderview)<br />
[RippleCircle](https://github.com/wusp/WuspViewPkg#ripplecircle)<br />
[PointIndicator&&ViewPagerIndicator](https://github.com/wusp/WuspViewPkg#pointindicator--viewpagerindicator)<br />
[DynamicMarkArea](https://github.com/wusp/WuspViewPkg#dynamicmarkarea)<br />
[ParticleHeartView](https://github.com/wusp/WuspViewPkg#particleheartview)<br />
[BounceFreeBar](https://github.com/wusp/WuspViewPkg#bouncefreebar)<br />
[ShrinkButton](https://github.com/wusp/WuspViewPkg#shrinkbutton)<br />

## FlashBorderView
![](https://github.com/wusp/WuspViewPkg/blob/master/app/src/main/java/com/wusp/wuspviewpkg/FlashBorder/flashborder.gif)

The FlashBorderView extends LinearLayout, so you can add the views you want just like using LinearLayout.
Animation is controlled by the parameter mFraction, so for each animation fraction you should invoke
``` Java
flashBorderView.setmFraction(int animationFraction);
flashBorderView.invalidate();   //from UI thread
flashBorderView.postInvalidate();   //from non-UI thread
```
to generate animation.
Also how this view perform animation has been defined by patternDrawer which is a instance implements Interface DynamicPatternDrawer. If you want to use your own animation effects, just using 
``` Java
flashBorderView.setPatternDrawer(DynamicPatternDrawer patternDrawer);
```
to change it.
####Custom Attributes
```
<attr name="border_width" format="dimension" />   //To indicate how width the border is.
<attr name="border_color" format="color" />   //To indicate the border color.
```

##RippleCircle
The RippleCircle is a circle that has a ripple animation effect, the content drawn on the circle center is not defined. You can dynamically invoke it by calling
``` Java
setPatternDrawer(StaticPatternDrawer patternDrawer);
```
or create a subclass of RippleCircle, for example ViewPagerIndicator on ViewPagerIndicatorActivity [here](https://github.com/wusp/WuspViewPkg/blob/master/app/src/main/java/com/wusp/wuspviewpkg/ViewPagerIndicator/ViewPagerIndicatorActivity.java).
####Custom Attributes
```
<attr name="circle_radius" format="dimension" />    //To indicate the radius of the circle(solid, ripple circle center).
<attr name="circle_color" format="color" />   //To indicate the background color of the circle.
<attr name="ripple_color" format="color" />   //To indicate the ripple effect color.
<attr name="ripple_width" format="dimension" />   //To indicate the ripple effect width.
```

FlashBorder and RippleCircle using example: <br />
![](https://github.com/wusp/WuspViewPkg/blob/master/app/src/main/java/com/wusp/wuspviewpkg/FlashBorder/flashborderripplecircle.gif)

##PointIndicator && ViewPagerIndicator
![](https://github.com/wusp/WuspViewPkg/blob/master/app/src/main/java/com/wusp/wuspviewpkg/ViewPagerIndicator/viewpagerindicator.gif)<br />
The PointIndicator is that series of points used to indicator something. PointIndicator extends LinearLayout, so you change the direction of points by setting the direction property.
Both of them are designed to work with ViewPager and easy to use, after defining on layout.xml, just invoke
```Java
indicator.setViewPager(ViewPager viewPager);  //ViewPagerIndicator
pointIndicator.setViewPager(ViewPager viewPager);   //PointIndicator
```
to bind to ViewPager, but PointIndicator also need to call
```Java
pointIndicator.setIndicatorNum(int count);
```
####Custom Attributes
PointIndicator
```
<attr name="indicator_width" format="dimension" />      //To indicate the single indicator width.
<attr name="indicator_height" format="dimension" />   //To indicate the signle indicatorHeigt.
<attr name="indicator_gap" format="dimension" />    //To indicate the distance between two indicators.
<attr name="indicator_animator" format="reference" />   //To indicate how does the indicator animates.
<attr name="indicator_selected_src" format="reference" />   //To indicate the selected indicator, probally is a drawable.
<attr name="indicator_unselected_src" format="reference" />   //To indicate the un-selected indicator.
```
ViewPagerIndicator
```
<attr name="small_text_color" format="color" />   //To indicate the small text color.
<attr name="big_text_color" format="color" />   //To indicate the big text color.
<attr name="slash_color" format="color" />    //To indicate the slash color.
<attr name="small_text_size" format="dimension" />    //To indicate the small text size.
<attr name="big_text_size" format="dimension" />    //To indicate the big text size.
```

##DynamicMarkArea
![](https://github.com/wusp/WuspViewPkg/blob/master/app/src/main/java/com/wusp/wuspviewpkg/MarkArea/markArea.gif)<br />
Nothing sepcial here. Just the example of using DynamicPatternDrawer interface.
To display different animation content, just to invoke
```Java
markArea.setDynamicPatternDrawer(DynamicPatternDrawer dynamicPatternDrawer);
markArea.perform();
```
then the view would perform the animation content you design.

##ParticleHeartView
![](https://github.com/wusp/WuspViewPkg/blob/master/app/src/main/java/com/wusp/wuspviewpkg/Particle/particleheart.gif)<br />
First, it's just an example of combining particle system and other animation effects (ex. circle-expanding and heart-beat here).
Second, the ParticleHeartView design idea comes from Twitter like button which you can see on Twitter applications.
Third, obviously the particle system is so great and so powerful, you can use particle system to make dramatic animations, don't be restricted by this example, just do your beautiful and amazing customization.

##BounceFreeBar
![](https://github.com/wusp/WuspViewPkg/blob/master/app/src/main/java/com/wusp/wuspviewpkg/ProgressBar/bounce-freedown.gif)<br />
Well, I think that may be this animation is too thin (is not!), which needs to cooperate with other animations to perform a more beautiful and splendid animation effect.
The time the bar is visible on window, the animation would be started automatically and stop itself when it's invisible on window.
####Custom Attributes
```
<attr name="point_color" format="color" />    //To indicate the three points color.
<attr name="line_color" format="color"/>    //To indicate the vibration line color.
<attr name="line_width" format="dimension" />   //To indicate the vibration line width.
<attr name="line_height" format="dimension" />    //To indicate the vibration line height.
```

##ShrinkButton
![](https://github.com/wusp/WuspViewPkg/blob/master/app/src/main/java/com/wusp/wuspviewpkg/ShrinkButton/shrinkbutton.gif)<br />
The ShrinkButton is just a rounded button which rectangle part would perform a diminishing animation (specific to a 45 px radius corner here.) and then perform an infinite dynamic animation at the center of the view (if there's an animation) as the follwing.

#License
MIT
