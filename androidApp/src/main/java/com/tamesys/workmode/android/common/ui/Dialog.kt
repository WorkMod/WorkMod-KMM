package com.tamesys.workmode.android.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogOkCancel(title: String,
                   mesage: String,
                   ok: String,
                   onOk: () -> Unit,
                   cancel: String,
                   onCancel: () -> Unit,
                   onDismiss: () -> Unit) {
    BasicAlertDialog(onDismissRequest = {
        onDismiss.invoke()
    }) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(title)
                Text(mesage)
                Row {
                    Button(onClick = { onCancel.invoke()}) {
                        Text(cancel)
                    }
                    Button(onClick = { onOk.invoke() }) {
                        Text(ok)
                    }
                }
            }
        }
    }

}