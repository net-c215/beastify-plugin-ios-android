import org.w3c.dom.HTMLElement
import org.w3c.dom.asList
import kotlin.browser.document
import kotlin.browser.window

/**
 * @author lusinabrian on 04/12/17.
 */
fun main(args: Array<String>) {
    if (window.asDynamic().hasRun == true) {
        return
    }

    window.asDynamic().hasRun = true

    browser.runTime.onMessage.addListener { message ->
        if (message.command === "beastify") {
            insertBeast(message.beastURL as String)
        } else if (message.command === "reset") {
            removeExistingBeasts()
        }
    }
}

fun insertBeast(beastUrl: String) {
    removeExistingBeasts()
    val beastImage = document.createElement("img") as HTMLElement
    beastImage.run {
        setAttribute("src", beastUrl)
        style.height = "100vh"
        className = "beastify-image"
    }

    document.body?.appendChild(beastImage)
}

fun removeExistingBeasts() {
    val existingBeasts = document.querySelectorAll(".beastify-image")

    for (beast in existingBeasts.asList()) {
        beast.parentNode?.removeChild(beast)
    }
}