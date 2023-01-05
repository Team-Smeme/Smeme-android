package com.sopt.smeme.system.storage

class LocalDataStoreStorage : LocalStorage {
    //private val lds = LocalDataStore

    override fun save(data: LocalStorage.Data) {
        if (data is UserData) {
            // lds.run { cr ->  }
        }
    }

    // TODO
    override fun get() = LocalStorage.EMPTY

    suspend fun save(accessToken: String?, refreshToken: String?) {
        // TODO
    }

    // TODO
    override fun isAuthenticated() = false
}