package net.rafaeltoledo.stak.data.api

data class ApiCollection<T>(val items: List<T>, val hasMore: Boolean)