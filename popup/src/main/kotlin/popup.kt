/**
 * @author lusinabrian on 04/12/17.
 */
import org.w3c.dom.Element
import kotlin.browser.document
import kotlin.js.Promise


const val SCRIPT_PATH = "/content_script/build/classes/kotlin/main/min"
const val CSS_HIDE_PAGE = """
    body > :not(.beastify-image) {
        display: none;
    }
"""

fun main(args: Array<String>) {
    Promise.all(
            arrayOf(
                    browser.tabs.executeScript(Script("$SCRIPT_PATH/kotlin.js")),
                    browser.tabs.executeScript(Script("$SCRIPT_PATH/content_script.js"))
            )
    )
            .then { listenForClicks() }
            .catch(::reportExecuteScriptError)
}

fun listenForClicks(){
    document.addEventListener("click", {
        val target = it.target as Element ?: return@addEventListener
        browser.tabs.query(Query(true,true))
                .then { handleClick(target, it[0].id) }
                .catch(::reportError)
    })
}

fun handleClick(target : Element, id : Int){
    if (target.classList.contains("beast")){
        val url = getUrl(target.textContent)
        browser.tabs.insertCSS(id, CssDetails(CSS_HIDE_PAGE))
        browser.tabs.sendMessage(id, jsObject {
            command = "beastify"
            beastURL = url
        })
    }else{
        browser.tabs.removeCSS(id, CssDetails(CSS_HIDE_PAGE))
        browser.tabs.sendMessage(id, jsObject {
            command = "reset"
        })
    }
}

fun reportError(error: Any) = console.error("Could not beastify: $error")

fun reportExecuteScriptError(error: Throwable) {
    document.querySelector("#popup-content")?.classList?.add("hidden")
    document.querySelector("#error-content")?.classList?.remove("hidden")
    console.error("Failed to execute beastify content script: ${error.message}")
}

fun getUrl(name: String?): String {
    val relative = "beasts/${name?.toLowerCase()}.jpg"
    return browser.extension.getURL(relative)
}

inline fun jsObject(init: dynamic.() -> Unit): dynamic {
    val o = js("{}")
    init(o)
    return o
}
