export default {
    AUTH_URL: 'http://localhost:8080/api/chat/auth',
    SIGN_UP_URL: 'http://localhost:8080/api/chat/signUp',
    SERVER_URL: 'http://localhost:8080/api/chat',
    APPLICATION_JSON: 'application/json',
    WEBSOCKET_URL: 'ws://localhost:8080/api/chat/api/chat/websocket',
    LONG_POOLING: 'http://localhost:8080/api/chat/api/chat/long-pooling',
    LONG_POOLING_CONNECT: 'http://localhost:8080/api/chat/api/chat/long-pooling/connect',
    LONG_POOLING_MESSAGES: 'http://localhost:8080/api/chat/api/chat/long-pooling?pageId=',
    APP : {
        LOGIN : '/login',
        CHAT : '/chat',
        REGISTRATION : '/registration',
        STOMP_URL: '/chat/stomp',
        STOMP_MESSAGE: '/app/api/dialog',
        SUBSCRIBE_URL: '/topic/messages'
    }
}
