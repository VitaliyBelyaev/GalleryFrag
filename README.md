# GalleryFrag

This is Android application that allows you to view photo collections from Unsplash.
Key points of how it works:
- Getting curated collections from Unsplash API endpoint using Retrofit
- Displaying photos using Picasso
- Display collections in ViewPager with title, one preview photo and description of each collection
- Tap on certain collection, opens 4 preview photos of that collection with "Open in browser" button in app bar 
- Clicking on "Open in browser" opens collection in-app using Google Custom Tabs (it will works if your default browser is Chrome)
- App has authentication to Unsplash with OAuth2 protocol
- When first time launch app you need to log in, then your access token will be store in SharedPreferences   
- In MainActivity we just navigating through fragments: 
  - MainFragment that holds ViewPager with items that are CollectionFragments
  - And PreviewFragment that replace MainFrgament in MainActivity when user clicks on certain collection 
