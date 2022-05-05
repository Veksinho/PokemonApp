# PokemonApp

In the implementatoin of this test, I have used Kotlin, PokeAPI, Retrofit, RecyclerView, Glide.

When opened, the app shows the list of pokemon on the main activity. Pokemon are displayed in a grid layout, which I found most fitting due to the lack of a search bar _(something I would have liked to have implemented)_. Data for the pokemon is fetched using Retrofit, which, along with the fetch function itself, is set up in the MainActivity code _(idealy this would maybe have been in a separate file)_. To obtain all the pokemon, I have added two query parameters to the get request, limit and offset. Limit has a fixed value of 20, and offset is passed as an argument to the fetch function and incremented by 20 with each call.

There are two models for the data, PokemonResponse, which is used to store the complete list of pokemon which is fetched, and Pokemon, which is used to store a single pokemon, a single element of the list. Pokemon name is taken from this, and for the pictures, since they are not in the model I had to work around it and take the pokemon id from the url and pass it to a url which contains the picture, which is obtained using Glide. The logic for this is stored in the PokemonListAdapter class.

RecyclerView is initialized in the main activity, where it is given an adapter, layout manager and onScrollListener. The logic for endless scrolling is contained in the onScrolled function override.

_Apart from the obvious fact that the app is unfinished, where it's missing the whole logic when the user clicks on a pokemon to display the detail, I think the app structure could be better optimized if some logic was stored in seperate files. With this, the whole app kind of lacks an architecture pattern and seems sloppy. Also, it would be better if the specific data for each pokemon was fetched from the api, not just the name and the url, that way, the images could also be more easily fetched. With some pokemon there is also a problem with spacing in the recycler view, where their names are too long, or their images are too close to eachother in the list. Another thing with images is that they are loaded slower than the names, so when the user scrolls fast, there is a slight delay with displaying the images._
