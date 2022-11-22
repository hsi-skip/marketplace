# Marketplace BackEnd Java

## Endpoints without authentification

### For get all products: 
	[GET] http://localhost:8080/api/product/all


### For get product by id
	[GET] localhost:8080/api/product/get-one/{1}

### Register new user

	localhost:8080/api/auth/register
	[RequestBody]
	{
		"username": "usert",
		"password": "user"
	}

### Login
	localhost:8080/api/auth/login
	[RequestBody]
	{
		"username": "username",
		"password": "password"
	}

###### First time you can login with
	{
		"username": "elon",
		"password": "twitter"
	}

	{
		"username": "bill",
		"password": "microsoft"
	}
	{
		"username": "tim",
		"password": "apple"
	}

## Endpoints with authentification

### For add a product 
	[POST] localhost:8080/api/product/add 

	[RequestBody]
	{
		"id": 555,
		"title": "Title 555",
		"description": "Description 555",
		"productImage": "URL 555",
		"price": 250,
		"publish": "PUBLISH",
		"owner": {
			"id" : 100
		}
	}

### For update a product 
	[PUT] localhost:8080/api/product/update?id=1

	[RequestBody]
	{
		"id": 1,
		"title": "Title updated",
		"description": "Description updated",
		"productImage": "URL updated",
		"price": 110,
		"publish": "UNPUBLISH"
	}

### For Publish or Unpublish a product
###### Publish a product
	[PUT] localhost:8080/api/product/publish?id=2&published=PUBLISH
	[Params]
		Request param id = 1
		Request param published=PUBLISH

###### Unpublish a product
	[PUT] localhost:8080/api/product/publish?id=2&published=UNPUBLISH
	[Params]
		Request param id = 1
		Request param published=PUBLISH
