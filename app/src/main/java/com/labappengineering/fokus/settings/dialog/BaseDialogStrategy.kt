package com.labappengineering.fokus.settings.dialog

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.labappengineering.fokus.data.Session
import com.labappengineering.fokus.settings.SessionItem

abstract class BaseDialogStrategy(
    protected val activity: Activity,
    protected val sessionItem: SessionItem
) : DialogStrategy<SessionItem> {
    protected fun sessionToSessionItemList(sess: Session) : ArrayList<SessionItem> {
        val sessionItems: ArrayList<SessionItem> = ArrayList(6)
        sessionItems.add(SessionItem("length", sess.length.toString(), "Session Length", SessionItem.ValueType.Int))
        sessionItems.add(SessionItem("shortBreak", sess.shortBreak.toString(), "Short Break Length", SessionItem.ValueType.Int))
        sessionItems.add(SessionItem("longBreak", sess.longBreak.toString(), "Long Break Length", SessionItem.ValueType.Int))
        sessionItems.add(SessionItem("sessionColor", sess.sessionColor, "Session Timer Color", SessionItem.ValueType.String))
        sessionItems.add(SessionItem("shortBreakColor", sess.shortBreakColor, "Short Break Timer Color", SessionItem.ValueType.String))
        sessionItems.add(SessionItem("longBreakColor", sess.longBreakColor, "Long Break Timer Color", SessionItem.ValueType.String))
        sessionItems.add(SessionItem("repetitions", sess.repetitions.toString(), "Number of sessions per round", SessionItem.ValueType.Int))
        sessionItems.add(SessionItem("perDay", sess.perDay.toString(), "Number of rounds per day", SessionItem.ValueType.Int))
        return sessionItems
    }

    protected fun sessionItemListToSession(sessionItemList: ArrayList<SessionItem>, sess: Session) : Session {
        return sess.copy(
            length = sessionItemList[0].value.toInt(),
            shortBreak = sessionItemList[1].value.toInt(),
            longBreak = sessionItemList[2].value.toInt(),
            sessionColor = sessionItemList[3].value,
            shortBreakColor = sessionItemList[4].value,
            longBreakColor = sessionItemList[5].value,
            repetitions = sessionItemList[6].value.toInt(),
            perDay = sessionItemList[7].value.toInt()
        )
    }
    protected fun findItemByKey(items: ArrayList<SessionItem>, key: String) : SessionItem? {
        for(item in items){
            if (item.key == key){
                return item
            }
        }
        return null
    }

    protected fun updateLiveData(
        item: SessionItem,
        items: MutableLiveData<ArrayList<SessionItem>>){
        val itemsList = ArrayList(items.value!!)
        val itemInList = findItemByKey(itemsList, item.key)
        if(itemInList != null){
            itemInList.value = item.value
            items.value!!.clear()
            items.value = itemsList
        }
    }
}