package com.example.gemipost.utils

import com.google.auth.oauth2.GoogleCredentials
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

object AccessToken {
    private val firebaseMessagingScope = "https://www.googleapis.com/auth/firebase.messaging"
    fun getAccessToken(): String {
        try{
            val jsonString = "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"gemipost\",\n" +
                    "  \"private_key_id\": \"3d9fdec91e19cc06214c8fa0ee62048026f56344\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCwNRbWqeAwghyl\\n4NVy8T9DA4zcO+greo3Lsmule+cMY9L4DZDd2Be65eBKDiss/HRkoG3YJIZGdxIO\\nzaJdgVxFV8wsPgW3PF75fLjrHe7x6HyZYKk8mMVsei7Z4JGRIuvy/23fZQ2GdFA/\\nZ8HixCYXLlSnvTuvh1pE6c0RuEYITuNjFNiEEjdinHvlxtkuoeylJasYfShrm9om\\nb4vVhZ1U3Vu0HunR3I7GhVKJMrKiYHr6+Fa4l8WTpfRuySgJ1ucTyVuzoJklVlZc\\nIj0SMPJKthPu+WPWXNODZZ+NrcDdZiQsejHyrnhQdC0GFFmYpfns6T2BF6kClelC\\nmMs99VX7AgMBAAECggEAVvAqr4krOuPHXLR8u/R5oQfMZRENsP4LnfgPEpv6WGuc\\nOJUA5+PpeWZX7lHlTZHVn5xj9eO3eGbhOvqMZJQhwXjbHO0RBuI7fd7ioean8SUi\\n7M37UhGSPr5eBVO+npxbTA5o2HLUZIVY3p/D0q2ymXdbf1NncmhWym+MpXSE4S2x\\nfSxl2NJSynNmTK7ByLuErHcPEg3aA2z7dVB/jnmNu8pcanwsO3PDlgviOU+eDRym\\nTW5WYumIhjd//PO6IL5PgXSVPd/LudNtU7cbaBk41EixyCyBB9viEcs2f8tXlEbX\\nxQnr247T7/g0x0P+IEaeAzByuIl3o0fs81+kGulG+QKBgQDWoXIO+emEWYoFxPyI\\n6lRGkkzhRavMWv0GReaHrGN+02YA/Fe7k+Pun3PbmT3A0Z751AKL1X5bC4ndzn74\\njsMKBVtLWLM+L0Ohv+l/VYI5jQgok8ZklI31tfTZcXgXMli1MHu0Gbg0lyowLCv6\\n2EM92qjRefkbTCTrxBP+Mk8G7QKBgQDSK7fe0bRiVoojeQSotH6QvLPqA3wlrBCt\\nPTyyPhK8C7TOLq46ZdeCC5ma71ctZNcLL745RgQx3u/CQfu770QHfnTZ69+bQE+I\\nEUHIEz5IhxIkQfMCY62ddm/kiyVBayhyRVlBFnbyCVySOD/4TUfenX6IUNeROtfA\\nqB5XfN6LhwKBgE5X6/xPeM+Yp2rnto57inugF3P7LHSRmd5aUYWghUjb/VXKxnNv\\nzjlh9rZnv0TMv0zN+If2TAMLuS0/nNU5tzPHNMXSAMtoETXPiXmzSq+bLSkyl65L\\nE25nFpLwejdtZzfsFJtu7/AyZXHkDcGogxLm58xey3ENYES2891Lm9EZAoGACMpF\\nTW9t4PMHogYsuLWb2Yfa6n2s0pKIvAYkpLvN4smGjV0McmrSAaFkkkNSXRarm88q\\nYJDaGg/d7Tz/P1P5f2q9jmO8HW0qJmG6Y5jScQwdKSEM/duXZk+OzHs5WqwVq/nU\\nitFatjLICWGK42KwD9J8eU0QRwW0Al3FrxkM4b8CgYEAkAua1n3uTa2Ryx3Emt3P\\njvGlIK3ENOAI/0PNLwaix9NAeFN4ZoJl0JiYkDnwCJVjvfJJ8AlFUA5q3R2yUpe0\\n4PGttf0OY34sgDMKIw+yu8n7V4fXPTYLR9woWsFPn1hGe/sIjQfmrRRo+A0WFA6S\\njPmLWQzmvKKRHd5J5G+xizE=\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-89c47@gemipost.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"101416383731527035005\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-89c47%40gemipost.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}"
            val stream = ByteArrayInputStream(jsonString.toByteArray(StandardCharsets.UTF_8))
            val googleCredentials = GoogleCredentials.fromStream(stream)
                .createScoped(arrayListOf(firebaseMessagingScope))
            googleCredentials.refresh()
            return googleCredentials.accessToken.tokenValue
        } catch(e: Exception) {
            e.printStackTrace()
            return ""
        }
    }
}