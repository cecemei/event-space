curl -I "http://api.meetup.com/2/topic_categories?page=40&key=6d75526a39297e167b781f4c3c6b3c" | jq '.' | cat

Content-Type: application/json;charset=utf-8
Content-Length: 22760
Server: Apache-Coyote/1.1
X-Meetup-server: 017e5f42ae39
X-Meetup-Request-ID: 7a567a4b-3eb3-46b1-9ef6-50e29d157146
X-RateLimit-Limit: 30
X-RateLimit-Remaining: 28
X-RateLimit-Reset: 0
X-OAuth-Scopes: basic
X-Accepted-OAuth-Scopes: basic
ETag: "e41236a484f01ee018853b8eccab2372"
Accept-Ranges: bytes
Date: Sun, 19 Nov 2017 07:56:05 GMT
Via: 1.1 varnish
Connection: keep-alive
X-Served-By: cache-pao17422-PAO
X-Cache: MISS
X-Cache-Hits: 0
X-Timer: S1511078165.209386,VS0,VE91
Vary: User-Agent,Accept-Language


curl "http://api.meetup.com/2/open_events?topic=spirituality&city=San+Francisco&state=CA&country=us&page=200&key=6d75526a39297e167b781f4c3c6b3c&time=%2C1w" | jq '.' | cat
