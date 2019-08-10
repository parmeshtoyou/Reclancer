package com.reclancer.common

interface ActionListener {

    fun onAction(action: String, data: Any? = null)

    /**
     * Custom Exception to handle exceptions related to ActionListener.
     * @param message is the message to be passed while raising exception.
     */
    class ActionListenerException(override var message: String) : Exception(message)
}