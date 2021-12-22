package com.example.walletlog

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.walletlog.contracts.SpendingId
import com.example.walletlog.contracts.SpendingUser
import com.example.walletlog.contracts.TableSpending
import java.lang.IllegalArgumentException

class SpendingContentProvider : ContentProvider() {

    private val authority = "com.chuduk.walletlog.authority";

    private val user_spending_list_path = "Users/#/Spending"
    private val user_spending_path = "Users/#/Spending/#"

    private val user_spending_type = "vnd.android.cursor.dir/vnd.$authority.$user_spending_list_path";

    private val user_spending_list = 300;
    private val user_spending = 301;

    private lateinit var uriMatcher: UriMatcher;
    private lateinit var dbHelper : SqliteDbHelper;

    override fun onCreate(): Boolean {
        dbHelper = SqliteDbHelper(context!!);
        uriMatcher = buildUriMatcher();
        return true;
    }

    private fun buildUriMatcher() : UriMatcher {
        val matcher = UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(authority, user_spending_list_path, user_spending_list);
        matcher.addURI(authority, user_spending_path, user_spending);

        return matcher;
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        setOrder: String?
    ): Cursor? {
        val db = dbHelper.readableDatabase;
        val match = uriMatcher.match(uri);

        when(match) {
            user_spending_list -> {
                val userId = uri.pathSegments[1];
                return db.query(TableSpending, projection, "$SpendingUser = ?", arrayOf(userId), null, null, setOrder);
            }
            user_spending -> {
                val userId = uri.pathSegments[1];
                val spendingId = uri.pathSegments[3];
                val select = "$SpendingUser = ? AND $SpendingId = ?";
                return db.query(TableSpending, projection, select, arrayOf(userId, spendingId), null, null, setOrder);
            }
            else -> throw IllegalArgumentException("Unknown uri: $uri");
        }
    }

    override fun getType(uri: Uri): String? {
        val match = uriMatcher.match(uri);

        when(match) {
            user_spending -> return user_spending_type;
            else -> throw IllegalArgumentException("Unknown uri: $uri");
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper.writableDatabase;
        val match = uriMatcher.match(uri);

        when(match) {
            user_spending_list -> {
                val userId = uri.pathSegments[1];
                val insertedValues = ContentValues();
                insertedValues.put(SpendingUser, userId);
                insertedValues.putAll(values);
                val id = db.insert(TableSpending, null, insertedValues);
                return Uri.parse("Users/$userId/Spending/$id");
            }
            else -> throw IllegalArgumentException("Unknown uri: $uri");
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper.writableDatabase;
        val match = uriMatcher.match(uri);

        when(match) {
            user_spending -> {
                val userId = uri.pathSegments[1];
                val spendingId = uri.lastPathSegment;
                return db.delete(TableSpending, "$SpendingUser = ? AND $SpendingId = ?", arrayOf(userId, spendingId));
            }
            else -> throw IllegalArgumentException("Unknown uri: $uri");
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper.writableDatabase;
        val match = uriMatcher.match(uri);

        when(match) {
            user_spending -> {
                val userId = uri.pathSegments[1];
                val spendingId = uri.lastPathSegment;
                return db.update(TableSpending, values, "$SpendingUser = ? AND $SpendingId = ?", arrayOf(userId, spendingId));
            }
            else -> throw IllegalArgumentException("Unknown uri: $uri");
        }
    }
}