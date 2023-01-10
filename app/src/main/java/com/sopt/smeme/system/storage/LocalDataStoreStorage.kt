package com.sopt.smeme.system.storage

import com.sopt.smeme.UserAccessType

class LocalDataStoreStorage : LocalStorage {

    override fun save(data: LocalStorage.Data) {
        // TODO
    }

    override fun get(accessType: UserAccessType) = LocalStorage.EMPTY

    // TODO
    override fun isAuthenticated() = false

    // TODO
    override fun isSocialAuthenticated() = false

    // TODO
    override fun authorize() = Unit

    // TODO
    override fun getAccessToken() = ""
}