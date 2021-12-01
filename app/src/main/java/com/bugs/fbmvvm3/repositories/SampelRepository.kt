package com.bugs.fbmvvm3.repositories

import com.bugs.fbmvvm3.Constants
import com.bugs.fbmvvm3.State
import com.bugs.fbmvvm3.model.Sampel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class SampelRepository {
    private val db = Firebase.firestore.collection(Constants.COLLECTION_SAMPEL)

    fun getAllSampel() = flow<State<List<Sampel>>>{

        // Emit loading state
        emit(State.loading())

        val snapshot = db
            .orderBy("creation", Query.Direction.DESCENDING)
            .get().await()
        val sampels = snapshot.toObjects(Sampel::class.java)

        // Emit success state with the data
        emit(State.success(sampels))
    }

    fun addSampel(sampel: Sampel) = flow<State<DocumentSnapshot>>{

        // Emit loading state
        emit(State.loading())

        val idSampel = sampel.creation.toString()

        val newSampel = db
            .document(idSampel)
            .set(sampel).await()

        val sampelRef = db
            .document(idSampel)
            .get()
            .await()

        // Emit success state with the sampel reference
        emit(State.success(sampelRef))

    }.catch {
        // if exception is thrown emit failed state along with message
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    fun deleteSampel(sampel: Sampel) = flow<State<Boolean>>{

        // Emit Loading state
        emit(State.loading())

        val idSampel = sampel.creation.toString()

        val sampelSnap = db
            .document(idSampel)
            .delete()
            .isSuccessful

        // Emit success state
        emit(State.success(sampelSnap))
    }.catch {
        emit(State.failed("Failed to delete"))
    }.flowOn(Dispatchers.IO)
}