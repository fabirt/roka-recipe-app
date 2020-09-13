package com.fabirt.roka.features.categories.constants

import com.fabirt.roka.features.categories.domain.model.Category
import com.fabirt.roka.features.categories.domain.model.CategoryItem

val categoriesList: List<Category> = listOf(
    Category(
        "cuisine",
        "Cuisines",
        listOf(
            CategoryItem("African", "https://images-cdn.newscred.com/Zz1iNmVhMTZhNDdiZTkyYWVmMjA4NDI5MzgyOTRiYjk3MQ==/Zz04MzhmMTcwMmMyMmUxZGQ3Y2M0YWNlZmM4OTcxNTdkOQ%3D%3D.jpeg?width=1200"),
            CategoryItem("American", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/69/Motherhood_and_apple_pie.jpg/1200px-Motherhood_and_apple_pie.jpg"),
            CategoryItem("British", "https://learnenglishteens.britishcouncil.org/sites/teens/files/styles/article/public/istock_000012858084small_0_0.jpg?itok=UaPalhkl"),
            CategoryItem("Caribbean", "https://img.theculturetrip.com/768x432/wp-content/uploads/2016/09/16558946119_70c3b69884_k.jpg"),
            CategoryItem("Chinese", "https://www.misstamchiak.com/wp-content/uploads/2019/12/IMG_0175-37-1300x867.jpg"),
            CategoryItem("European", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR2p1oQ1KqzC64TD9MYSbD_CgKAvFWyj2XPSA&usqp=CAU"),
            CategoryItem("French", "https://www.recetasdesbieta.com/wp-content/uploads/2018/10/lasagna-original.-300x169.jpg"),
            CategoryItem("German", "https://img.theculturetrip.com/768x432/wp-content/uploads/2017/12/shutterstock_749694442.jpg"),
            CategoryItem("Greek", "https://bloximages.newyork1.vip.townnews.com/laduenews.com/content/tncms/assets/v3/editorial/1/1c/11caed69-7537-5f09-a194-cacfb0c0d56c/5ddd424d236ce.image.jpg?resize=1200%2C800"),
            CategoryItem("Indian", "https://miro.medium.com/max/1000/1*8lQ9FLOtT0lN6_D4I93Mcw.jpeg"),
            CategoryItem("Irish", "https://chowhound1.cbsistatic.com/thumbnail/800/0/chowhound1.cbsistatic.com/blog-media/2019/03/Chowhound-Ground-Beef-Shepherds-Pie-670x447.jpg"),
            CategoryItem("Italian", "https://www.samuirestaurantguide.com/wp-content/uploads/2016/01/italian-food.jpg"),
            CategoryItem("Japanese", "https://jpninfo.com/wp-content/uploads/2018/03/sushi-platter.jpg"),
            CategoryItem("Jewish", "https://www.myjewishlearning.com/wp-content/uploads/2017/02/iStock-629077122-e1487798855179.jpg"),
            CategoryItem("Korean", "https://www.matchingfoodandwine.com/files/blogattachments/top/korean-pork-dish@2x.jpg"),
            CategoryItem("Latin American", "https://wearecocina.files.wordpress.com/2017/10/salvadorean-empanadas.jpg?w=1152"),
            CategoryItem("Mexican", "https://www.englishclub.com/images/vocabulary/food/mexican/mexican-food.jpg"),
            CategoryItem("Nordic", "https://honestcooking.com/wp-content/uploads/2015/09/shutterstock_279810137.jpg"),
            CategoryItem("Spanish", "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fb/Paella-mixta.jpg/440px-Paella-mixta.jpg"),
            CategoryItem("Thai", "https://media-cdn.tripadvisor.com/media/photo-s/17/22/fb/04/jaisungma-thai-cuisine.jpg"),
            CategoryItem("Vietnamese", "https://media-cdn.tripadvisor.com/media/photo-s/1a/32/e7/70/updated-menu.jpg")
        )
    ),
    Category(
        "diet",
        "Diets",
        listOf(
            CategoryItem("Gluten Free", "https://i0.wp.com/www.diegocoquillat.com/wp-content/uploads/2018/11/sin_gluten2.png?fit=702%2C336&ssl=1"),
            CategoryItem("Ketogenic", "https://i.blogs.es/aa8690/aguacate_huevos/450_1000.jpg"),
            CategoryItem("Vegetarian", "https://i0.wp.com/images-prod.healthline.com/hlcmsresource/images/AN_images/vegetarian-diet-plan-1296x728-feature.jpg?w=1155&h=1528"),
            CategoryItem("Lacto-Vegetarian", "https://s3-us-west-1.amazonaws.com/contentlab.studiod/getty/d817388bbe294ace959d7b55f42fca0e.jpg"),
            CategoryItem("Ovo-Vegetarian", "https://caitlinbenham.weebly.com/uploads/4/5/2/7/45271833/9122438.jpg?305"),
            CategoryItem("Vegan", "https://cdn.loveandlemons.com/wp-content/uploads/2019/12/vegan-recipes-500x375.jpg"),
            CategoryItem("Pescetarian", "https://st4.depositphotos.com/9012638/29097/i/450/depositphotos_290973290-stock-photo-pescetarian-diet-plan-ingredients.jpg"),
            CategoryItem("Paleo", "https://irenamacri.com/wp-content/uploads/2017/05/paleo-shrimp-stir-fry-feature.jpg"),
            CategoryItem("Primal", "https://quediferenciahay.com/wp-content/uploads/2016/11/dieta-paleo-y-dieta-primal-SFW.jpg")
        )
    ),
    Category(
        "type",
        "Meal Types",
        listOf(
            CategoryItem("Main Course", "https://img.taste.com.au/WMldfrFD/taste/2016/11/chicken-and-prosciutto-parmigiana-79468-1.jpeg"),
            CategoryItem("Side Dish", "https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fimg1.cookinglight.timeinc.net%2Fsites%2Fdefault%2Ffiles%2Fstyles%2F4_3_horizontal_-_1200x900%2Fpublic%2Fimage%2F2017%2F06%2Fmain%2Faromatic-slow-roasted-tomatoes-0912p189.jpg%3Fitok%3DMk2U-JxY"),
            CategoryItem("Dessert", "https://prods3.imgix.net/images/articles/2018_04/nonfeatured-chicago-best-desserts.jpg?auto=format%2Ccompress&dpr=2.63&ixjsv=2.2.3&q=38&w=370"),
            CategoryItem("Appetizer", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/christmas-appetizers-1574193020.jpg?crop=1.00xw:0.668xh;0,0.155xh&resize=640:*"),
            CategoryItem("Salad", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/delish-190611-mandarin-orange-salad-281-landscape-pf-1561556605.png"),
            CategoryItem("Bread", "https://www.cambiatufisico.com/wp-content/uploads/pan-y-musculos.jpg"),
            CategoryItem("Breakfast", "https://cdn.tasteatlas.com/images/dishes/b92a3d74b4cf48c28abba34197798049.jpg?w=600&h=450"),
            CategoryItem("Soup", "https://realfood.tesco.com/media/images/Chinese-chicken--sweetcorn-soup-recipe-1400x919-3078923d-9838-433c-bcb4-69c5521055b5-0-1400x919.jpg"),
            CategoryItem("Beverage", "https://firmenich.cdn.oxv.fr/uploads/images/02_flavors/02_beverage/glaces_of_ice_tea.jpg?0f9188a"),
            CategoryItem("Sauce", "https://www.seriouseats.com/recipes/images/2014/09/20140919-easy-italian-american-red-sauce-vicky-wasik-19.jpg"),
            CategoryItem("Marinade", "https://www.jessicagavin.com/wp-content/uploads/2018/07/chicken-marinade-5-1200.jpg"),
            CategoryItem("Fingerfood", "https://cdn.pixabay.com/photo/2019/04/13/20/22/chunks-4125516_960_720.jpg"),
            CategoryItem("Snack", "https://www.bakingbusiness.com/ext/resources/2020/6/SnackVariety_Lead.jpg?1591036941"),
            CategoryItem("Drink", "https://hips.hearstapps.com/del.h-cdn.co/assets/16/25/1466718212-delish-lemonade-cheatsheet-index.jpg")
        )
    ),
    Category(
        "intolerances",
        "Intolerances",
        listOf(
            CategoryItem("Dairy", "https://static01.nyt.com/images/2019/02/19/well/ask-food/ask-food-superJumbo.jpg"),
            CategoryItem("Egg", "https://cdn.britannica.com/94/151894-050-F72A5317/Brown-eggs.jpg"),
            CategoryItem("Gluten", "https://www.healthline.com/hlcmsresource/images/AN_images/gluten-free-diet-thumb.jpg"),
            CategoryItem("Grain", "https://www.healthline.com/hlcmsresource/images/imce/allergies-wheat_thumb.jpg"),
            CategoryItem("Peanut", "https://cdn-prod.medicalnewstoday.com/content/images/articles/317/317540/caution-sign-representing-a-peanut-allergy.jpg"),
            CategoryItem("Seafood", "https://home.allergicchild.com/wp-content/uploads/2012/07/fish_shellfish.jpg"),
            CategoryItem("Sesame", "https://i0.wp.com/post.healthline.com/wp-content/uploads/2019/08/sesame-seeds-spoon-1296x728-header-1-1296x728.jpg?w=1155&h=1528"),
            CategoryItem("Shellfish", "https://www.unlockfood.ca/EatRightOntario/media/Website-images-resized/Facts-on-seafood-and-fish-allergies-original-resized.jpg"),
            CategoryItem("Soy", "https://www.allergiesandhealth.com/wp-content/uploads/soy-soya-700x433.jpg"),
            CategoryItem("Sulfite", "https://www.unlockfood.ca/EatRightOntario/media/Website-images-resized/Dried-apricots-resized.jpg"),
            CategoryItem("Tree Nut", "https://www.unlockfood.ca/EatRightOntario/media/Website-images-resized/Facts-on-tree-nut-allergies-resized.jpg"),
            CategoryItem("Wheat", "https://checkmybodyhealth.co.nz/app/uploads/2019/07/coeliac-image.jpg")
        )
    )
)