# Test Cases for Swagger Petstore API

## 1. Create a New Pet (POST /pet)
- Create pet with valid id, name, status (should succeed)
- Create pet with missing fields (should fail)
- Create pet with negative id (should fail)
- Create pet with empty name/status (should fail)

## 2. Retrieve Pet by ID (GET /pet/{petId})
- Get pet with valid id (should succeed)
- Get pet with invalid/nonexistent id (should return 404)
- Get pet with negative id (should return 404)
- Get pet with already deleted id (should return 404)

## 3. Update an Existing Pet (PUT /pet)
- Update pet with valid id (should succeed)
- Update pet with invalid id (should fail)
- Update pet with missing fields (should fail)
- Update pet with negative id (should fail)

## 4. Delete a Pet (DELETE /pet/{petId})
- Delete pet with valid id (should succeed)
- Delete pet with invalid/nonexistent id (should return 404)
- Delete pet with negative id (should return 404)
- Delete pet with already deleted id (should return 404)

## 5. Find Pets by Status (GET /pet/findByStatus)
- Find pets with status 'available' (should return list)
- Find pets with status 'pending' (should return list)
- Find pets with status 'sold' (should return list)
- Find pets with invalid status (should return 400)

## 6. Complete Pet Lifecycle
- Create, get, update, delete, and verify deletion of a pet


