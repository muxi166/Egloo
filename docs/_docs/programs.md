---
layout: page
title: "Programs"
description: "Egloo programs draw inside the drawable shape"
order: 3
disqus: 1
---

After you know what to draw through drawables, Egloo needs a `GlProgram` implementation to control
**how to draw** them. In Android terms, you can think of this as the `Paint` object that's used to
draw on canvas.

In GLES terms, a `GlProgram` is exactly an OpenGL program. It accepts a vertex shader and a fragment
shader in the constructor and manages the program itself.

A `GlProgram` can be used to draw one or more drawables using the draw method:

```kotlin
program.draw(glDrawable1)
program.draw(glDrawable2, mvpMatrix) // Optional
```

If not present, the model-view-projection matrix will be considered equal to the drawable's model
matrix. As with most other components, after usage, all programs should be released:

```kotlin
program.release()
```

Egloo offers two base program implementations.

### Flat program

The simplest program is one that draws the `GlDrawable` with a flat color. This program is called
`GlFlatProgram`:

```kotlin
val program = GlFlatProgram()
program.setColor(Color.RED)
program.draw(drawable1)
program.setColor(Color.GREEN)
program.draw(drawable2)
```

### Texture program

The `GlTextureProgram` program can be used to render texture frames. It will automatically generate
a `textureId` for you, which is meant to be used to create `SurfaceTexture`s. This means that it 
uses the `GLES11Ext.GL_TEXTURE_EXTERNAL_OES` texture target.

See the sample below:

```kotlin
val program = GlTextureProgram()
val programTexture = SurfaceTexture(program.textureId)

// Pass this surfaceTexture to Camera, for example
val camera: android.hardware.Camera = openCamera()
camera.setPreviewTexture(programTexture)

// Now the program texture receives the camera frames
// And we can render them using the program
val rect = GlRect() // Draw the full frame
programTexture.getTransformMatrix(program.textureTransform)
program.draw(rect)
```