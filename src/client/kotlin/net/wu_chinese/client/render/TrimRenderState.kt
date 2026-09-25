package net.wu_chinese.client.render

object TrimRenderState {
  private val glowing = ThreadLocal.withInitial { false }
  private val shimmering = ThreadLocal.withInitial { false }

  fun capture(isGlowing: Boolean, isShimmering: Boolean) {
    glowing.set(isGlowing)
    shimmering.set(isShimmering)
  }

  fun isShimmering(): Boolean = shimmering.get()

  fun isGlowing(): Boolean = glowing.get()
}
