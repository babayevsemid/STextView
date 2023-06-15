# STextView

## Screenshot


| type  | gif |
| :-- | :-- |
| Scale     | ![](https://github.com/babayevsemid/STextView/blob/master/screenshot/demo3.gif) |
| Evaporate | ![](https://github.com/babayevsemid/STextView/blob/master/screenshot/demo5.gif) |
| Fall      | ![](https://github.com/babayevsemid/STextView/blob/master/screenshot/demo6.gif) |
| Typer     |  ![](https://github.com/babayevsemid/STextView/blob/master/screenshot/typer.gif) |
| Rainbow   | ![](https://github.com/babayevsemid/STextView/blob/master/screenshot/rainbow.gif) |
| Fade      | ![](https://github.com/babayevsemid/STextView/blob/master/screenshot/fade.gif) |

## Usage


```
implementation 'com.github.babayevsemid:STextView:1.0.0'
```

### fade

```
<com.samid.stextview.widget.FadeTextView
    android:layout_width="240dp"
    android:layout_height="150dp"
    android:gravity="left"
    android:letterSpacing="0.08"
    android:lineSpacingMultiplier="1.3"
    android:text="This is FadeTextView"
    android:textColor="#fff"
    android:textSize="20sp"
    app:animationDuration="1500"/>
```

### typer

```
<com.samid.stextview.widget.TyperTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="this is init sentence."
    app:charIncrease="3"
    app:typerSpeed="80"/>
```

### rainbow

```
<com.samid.stextview.widget.RainbowTextView
    android:layout_width="120dp"
    android:layout_height="wrap_content"
    android:gravity="right"
    android:text="this is init sentence"
    android:textSize="20sp"
    app:colorSpace="150dp"
    app:colorSpeed="4dp"/>
```

### scale (single line)

```
<com.samid.stextview.widget.ScaleTextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="this is init sentence"
    android:textSize="16sp"
    app:animationDuration="300"/>
```


### evaporate (single line)

```
<com.samid.stextview.widget.EvaporateTextView
    android:layout_width="match_parent"
    android:layout_height="100dp"
    android:gravity="center"
    android:paddingTop="8dp"
    android:text="this is init sentence"
    android:textSize="20sp"/>
```

### fall  (single line)

```
<com.samid.stextview.widget.FallTextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:paddingBottom="20dp"
    android:text="this is init sentence"
    android:textSize="16sp"/>
```
