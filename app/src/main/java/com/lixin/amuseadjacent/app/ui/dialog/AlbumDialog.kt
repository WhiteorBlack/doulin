package com.lixin.amuseadjacent.app.ui.dialog

import android.app.AlertDialog
import android.content.Context

/**
 * 相册操作
 * Created by Slingge on 2018/9/26
 */
object AlbumDialog {

    interface AlbumEditCallBal {
        fun albumedit(flag: Int)
    }

    fun dialog(context: Context, albumEditCallBal: AlbumEditCallBal) {
        val dialog = AlertDialog.Builder(context)
//        dialog.setTitle(title)
//        dialog.setMessage("")
        dialog.setPositiveButton("设为头像"
        ) { arg0, arg1 ->
            albumEditCallBal.albumedit(0)
        }
        dialog.setNegativeButton("删除") { arg0, arg1 ->
            albumEditCallBal.albumedit(1)
        }
        dialog.show()
    }

}