package com.susee.zap

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.susee.zap.utils.ConnectionState
import com.susee.zap.utils.currentConnectivityState
import com.susee.zap.utils.networkCallback
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ConnectivityTest {
    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockConnectivityManager: ConnectivityManager

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        `when`(mockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(
                mockConnectivityManager
            )
    }

    @Test
    fun testCurrentConnectivityState() {
        // Prepare mock behavior
        val networkCapabilities = mock(NetworkCapabilities::class.java)
        `when`(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).thenReturn(
                true
            )

        val network = mock(Network::class.java)
        `when`(mockConnectivityManager.allNetworks).thenReturn(arrayOf(network))
        `when`(mockConnectivityManager.getNetworkCapabilities(network)).thenReturn(
                networkCapabilities
            )

        // Test the extension property
        val connectionState = mockContext.currentConnectivityState
        assertEquals(ConnectionState.Available, connectionState)
    }

    @Test
    fun testNetworkCallback_onAvailable() {
        val callback: (ConnectionState) -> Unit = mock()
        val networkCallback = networkCallback(callback)

        networkCallback.onAvailable(mock(Network::class.java))

        verify(callback).invoke(ConnectionState.Available)
    }

    @Test
    fun testNetworkCallback_onLost() {
        val callback: (ConnectionState) -> Unit = mock()
        val networkCallback = networkCallback(callback)

        networkCallback.onLost(mock(Network::class.java))

        verify(callback).invoke(ConnectionState.Unavailable)
    }
}