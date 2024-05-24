package com.example.proyectofinaltfg.TFGAPP.ui.viewModel.UserVM

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.proyectofinaltfg.TFGAPP.data.Model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Test
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runBlockingTest


class UserProfileVMTest {


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()


    private lateinit var viewModel: UserProfileVM
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        auth = mockk(relaxed = true)
        firestore = mockk(relaxed = true)
        viewModel = spyk(UserProfileVM(), recordPrivateCalls = true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun fetchUser_success() = runBlockingTest {
        // Arrange
        val email = "test@example.com"
        val userId = "testUserId"
        val userModel = UserModel(
            userId = userId,
            email = email,
            pasww = "password",
            userName = "TestUserName",
            name = "Test Name",
            foto = "photoUrl"
        )

        every { auth.currentUser?.email } returns email

        val documentSnapshot = mockk<DocumentSnapshot> {
            every { id } returns userId
            every { toObject<UserModel>() } returns userModel
        }

        val querySnapshot = mockk<QuerySnapshot> {
            every { documents } returns listOf(documentSnapshot)
        }

        val listenerRegistration = mockk<ListenerRegistration>()

        coEvery {
            firestore.collection("Users").whereEqualTo("email", email).addSnapshotListener(any())
        } answers {
            val callback = it.invocation.args[0] as (QuerySnapshot?, FirebaseFirestoreException?) -> Unit
            callback(querySnapshot, null)
            listenerRegistration
        }

        // Act
        viewModel.fetchUser()

        // Assert
        assertEquals(userModel.name, viewModel.name)
        assertEquals(userModel.userName, viewModel.userName)
        assertEquals(userModel.email, viewModel.eemail)
        assertEquals(userModel.pasww, viewModel.pasww)
        assertEquals(userModel.pasww, viewModel.repatPass)
        assertEquals(userModel.foto, viewModel.photoUrl)
    }


}