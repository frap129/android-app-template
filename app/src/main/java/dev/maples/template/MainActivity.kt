package dev.maples.template

import android.os.Bundle
import androidx.activity.compose.setContent
import core.lifecycle.TemplateActivity
import core.ui.App
import core.ui.model.data.Destination

class MainActivity : TemplateActivity() {
    /**
     * Holds Destination objects for all navigable screens in the app.
     * This list is consumed by NavHost to add the defined routes to
     * the NavGraph.
     *
     * @property destinations a list of [core.ui.model.data.Destination]
     */
    private val destinations: List<Destination> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(destinations)
        }
    }
}
