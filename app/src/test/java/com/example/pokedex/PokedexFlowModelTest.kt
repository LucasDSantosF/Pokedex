package com.example.pokedex

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import app.cash.turbine.test
import com.example.pokedex.model.service.PokedexService
import com.example.pokedex.ui.step.PokedexFlowModel
import com.example.pokedex.ui.step.PokedexState
import com.example.pokedex.ui.step.PokedexStateData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class PokedexFlowModelTest {
    private lateinit var flowModel: PokedexFlowModel
    private lateinit var service: FakePokedexService

    private val dispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule(dispatcher)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        service = FakePokedexService()

        flowModel = PokedexFlowModel(
            service = service
        )
    }

    @Test
    fun `should return correct pokemon list`() = runTest(dispatcher) {
        flowModel.getList()

        flowModel.state.asFlow().test {
            advanceUntilIdle()

            when (val result = expectMostRecentItem()) {
                is PokedexState.Result ->
                    assertEquals(PokedexTestData.pokemonList().results, result.state.list)

                else -> assert(false)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should return correct pokemon type list`() = runTest(dispatcher) {
        flowModel.getList()

        flowModel.state.asFlow().test {
            advanceUntilIdle()

            when (val result = expectMostRecentItem()) {
                is PokedexState.Result ->
                    assertEquals(PokedexTestData.pokemonDetail().types, result.state.typeList)

                else -> assert(false)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should return correct pokemon list by type`() = runTest(dispatcher) {
        flowModel.getPokemonByType(stateData = PokedexStateData(), "some-id")

        flowModel.state.asFlow().test {
            advanceUntilIdle()

            when (val result = expectMostRecentItem()) {
                is PokedexState.Result ->
                    assertEquals(PokedexTestData.pokemonList().results, result.state.list)

                else -> assert(false)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should return correct pokemon detail`() = runTest(dispatcher) {
        flowModel.getDetail(stateData = PokedexStateData(), "some-id")

        flowModel.state.asFlow().test {
            advanceUntilIdle()

            when (val result = expectMostRecentItem()) {
                is PokedexState.Result ->
                    assertEquals(PokedexTestData.pokemonDetail(), result.state.details)

                else -> assert(false)
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should reload list if coming to detail to list and pokemon type selected`() =
        runTest(dispatcher) {
            flowModel.updateSelectedType("3", stateData = PokedexStateData(), preSelectedType = null)

            flowModel.state.asFlow().test {
                advanceUntilIdle()

                when (val result = expectMostRecentItem()) {
                    is PokedexState.Result ->
                        assertEquals("3", result.state.selectedType)

                    else -> assert(false)
                }
                cancelAndIgnoreRemainingEvents()
            }
        }
}

@ExperimentalCoroutinesApi
class CoroutineTestRule(
    private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}