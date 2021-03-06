package com.otaliastudios.opengl.core

import android.opengl.*
import com.otaliastudios.opengl.internal.EglContext

/**
 * Core EGL state (display, context, config).
 * The EGLContext must only be attached to one thread at a time.
 * This class is not thread-safe.
 *
 * @param sharedContext The context to share, or null if sharing is not desired.
 * @param flags Configuration bit flags, e.g. FLAG_RECORDABLE.
 */
actual class EglCore @JvmOverloads constructor(
        sharedContext: EGLContext? = EGL14.EGL_NO_CONTEXT,
        flags: Int = 0
) : EglNativeCore(EglContext(sharedContext), flags) {

    /**
     * Discards all resources held by this class, notably the EGL context.  This must be
     * called from the thread where the context was created.
     * On completion, no context will be current.
     */
    public override fun release() {
        super.release()
    }

    /**
     * Makes this context current, with no read / write surfaces.
     */
    public override fun makeCurrent() {
        super.makeCurrent()
    }

    // Kotlin has no finalize, but simply declaring it works,
    // as stated in official documentation.
    protected fun finalize() {
        release()
    }

    companion object {

        /**
         * Constructor flag: surface must be recordable. This discourages EGL from using a
         * pixel format that cannot be converted efficiently to something usable by the video
         * encoder.
         */
        const val FLAG_RECORDABLE = EglNativeCore.FLAG_RECORDABLE

        /**
         * Constructor flag: ask for GLES3, fall back to GLES2 if not available. Without this
         * flag, GLES2 is used.
         */
        const val FLAG_TRY_GLES3 = EglNativeCore.FLAG_TRY_GLES3
    }
}