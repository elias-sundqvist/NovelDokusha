package my.noveldokusha.ui.databaseBookInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import my.noveldokusha.BookMetadata
import my.noveldokusha.Response
import my.noveldokusha.fetchDoc
import my.noveldokusha.scrubber

class DatabaseBookInfoModel : ViewModel()
{
	private var initialized = false
	fun initialization(database: scrubber.database_interface, bookMetadata: BookMetadata)
	{
		if (initialized) return else initialized = true
		this.database = database
		this.bookMetadata = bookMetadata
	}
	
	val relatedBooks = ArrayList<BookMetadata>()
	val similarRecommended = ArrayList<BookMetadata>()
	
	lateinit var database: scrubber.database_interface
	lateinit var bookMetadata: BookMetadata
	
	val bookDataLiveData by lazy {
		flow {
			val doc = fetchDoc(bookMetadata.url)
			emit(Response.Success(database.getBookData(doc)))
		}.flowOn(Dispatchers.IO).asLiveData()
	}
}