

import json
import secrets
import bcrypt

from django.views import View
from django.http import HttpResponse
from django.http import JsonResponse
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_http_methods
from django.shortcuts import get_object_or_404

from DododexApi.models import CustomUser, UserSession, Dinosaur, Favorite, Museum

# Create your views here.

@csrf_exempt
def sessions(request):
    if request.method != "POST":
        return JsonResponse({"error": "Not supported HTTP method"}, status=405)

    body_json = json.loads(request.body)
    required_params = ["email", "password"]
    missing_params = [param for param in required_params if param not in body_json]

    if missing_params:
        return JsonResponse({"error": "You are missing a parameter"}, status=400)

    json_email = body_json["email"]
    json_password = body_json["password"]

    try:
        db_user = CustomUser.objects.get(email=json_email)

    except CustomUser.DoesNotExist:
        return JsonResponse({"error": "User not in database"}, status=404)

    if bcrypt.checkpw(json_password.encode("utf8"), db_user.password.encode("utf8")):
        random_token = secrets.token_hex(10)
        session = UserSession(user=db_user, token=random_token)
        session.save()
        return JsonResponse({"token": random_token}, status=201)

    return JsonResponse({"error": "Invalid password"}, status=401)

@csrf_exempt
def register(request):
    if request.method != "POST":
        return JsonResponse({"error": "Unsupported HTTP method"}, status=405)

    body_json = json.loads(request.body)
    required_params = ["name", "email", "password"]

    if not all(param in body_json for param in required_params):
        return JsonResponse({"error": "Missing parameter in body request"}, status=400)

    json_name = body_json["name"]
    json_email = body_json["email"]
    json_password = body_json["password"]

    if CustomUser.objects.filter(name=json_name).exists():
        return JsonResponse({"error": "Username already exist"}, status=400)

    if "@" not in json_email or len(json_email) < 5:
        return JsonResponse({"error": "Not valid email"}, status=400)

    if CustomUser.objects.filter(email=json_email).exists():
        return JsonResponse({"error": "User already registered."}, status=401)

    salted_and_hashed_pass = bcrypt.hashpw(
        json_password.encode("utf8"), bcrypt.gensalt()
    ).decode("utf8")

    user_object = CustomUser(
        name=json_name, email=json_email, password=salted_and_hashed_pass
    )
    user_object.save()

    # Generamos un token para el usuario
    token = secrets.token_hex(16)
    return JsonResponse({"token": token}, status=200)

@require_http_methods(["PUT"])
@csrf_exempt
def update_dinosaur_like_status(request, id):
    try:
        user_token = request.headers.get('user-token')
    except:
        return JsonResponse({"error": "User token is required"}, status=400)
    try:
        # Obtener la sesión de usuario asociada al token
        user_session = get_object_or_404(UserSession, token=user_token)
        # Obtener el objeto Dinosaurio basado en el ID proporcionado en la URL
        dinosaur = get_object_or_404(Dinosaur, id=id)

        try:
            user_db = user_session.user  # Assuming UserSession has a 'user' field
            dino = dinosaur  # Assuming Dinosaur is the correct model

        except UserSession.DoesNotExist:
            return JsonResponse({"error": "User not found"}, status=404)

        except Dinosaur.DoesNotExist:
            return JsonResponse({"error": "Dinosaur not found"}, status=404)

        data = json.loads(request.body)
        liked = data['liked']

        if liked:
            favorite = Favorite(user=user_db, dinosaur=dino)
            favorite.save()
            return JsonResponse({"message": "Its clear"}, status=200)
        else:
            if Favorite.objects.filter(user=user_db, dinosaur=dino).exists():
                Favorite.objects.get(user=user_db, dinosaur=dino).delete()
                return JsonResponse({"message": "The like data has been changed."}, status=200)

    except:
        return JsonResponse({"error": "Invalid JSON data in request body"}, status=400)
    
@csrf_exempt
def session(request):

    # In case of the method is not DELETED, we return a JsonResponse with fail code
    if request.method != 'DELETE':
        return JsonResponse({'Error': 'Method not allowed'}, status=405)
    
    # Obteining the session token and checking if it is not none
    try:
        user_token = request.headers.get("user-token")

        if user_token is None:
            return JsonResponse({'Error': 'Token is none'}, status=401)
        
    except:
        return JsonResponse({'Error': 'Token Missing'}, status=401)

    # Checking if the session exists
    if not UserSession.objects.filter(token=user_token).exists():
        return JsonResponse({'error': 'User not authenticated.'}, status=401)

    # Deleting the session
    UserSession.objects.get(token=user_token).delete()
    return JsonResponse({'Info': 'Session has been deleted'}, status=200)

# Get user data from BBDD
def user_data(request):

    # In case of the method is not GET, we return a JsonResponse with fail code
    if request.method == 'GET':
        user_token = request.headers.get('user-token')

         # In case of the token is none, we return a JsonResponse with fail code, otherwise we return the token
        if not user_token:
             return JsonResponse({'Error': 'Token is required'}, status=401)

        if not UserSession.objects.filter(token=user_token).exists():
             return JsonResponse({'error': 'User not authenticated.'}, status=401)


        try:
             # Get the user (CustomUser) from BBDD using foreign key
             user = UserSession.objects.get(token=user_token).user

        except:
             return JsonResponse({'Error': 'User not found or invlaid'}, status=404)

         # Set a variable with all the fields from the user.  
        data = {'name' : user.name,
                 'surname' : user.surname,
                 'password' : user.password,
                 'email' : user.email,
                 'telephone' : user.telephone # In case of this is null in BBDD, it returns null value in json repsonse.
                 }

        return JsonResponse(data, status= 200)

    if request.method == 'PUT':

        if json.loads(request.body.decode('utf-8')) is None:
            return JsonResponse({"error": "No data send in body"}, status=400)

        data = json.loads(request.body.decode('utf-8'))

        #Obtener el token del usuario desde los encabezados
        try:
            user_token = request.headers.get('user-token')
            UserSession.objects.filter(token=user_token).exists()
        except UserSession.DoesNotExist:
            return JsonResponse({"error": "User not logged in"}, status=401)

        # Obtener el usuario con el token con el token proporcionado
        user = UserSession.objects.get(token=user_token).user
        if data[0].get('name') is not None:
            user.name = data[0].get('name')

        if data[0].get('surname') is not None:
            user.surname = data[0].get('surname')

        if data[0].get('email') is not None:
            user.email = data[0].get('email')

        if data[0].get('password') is not None:
            user.password = bcrypt.hashpw(
                data[0].get('password').encode("utf8"), bcrypt.gensalt()
            ).decode("utf8")

        if data[0].get('telephone') is not None:
            user.telephone = data[0].get('telephone')

        user.save()
        return JsonResponse({"message": "Los datos han sido cambiados", "user_data": model_to_dict(user)})

    return JsonResponse({'Error': 'Method not supported'}, status=405)

@csrf_exempt
def eliminar_cuenta(request):

    if request.method != 'DELETE':
        return JsonResponse({'Error': 'Método no permitido'})
    
    try:
        user_token = request.headers.get('user-token')
        UserSession.objects.get(token=user_token)
    except UserSession.DoesNotExist:
        return JsonResponse({'error': 'Usuario no encontrado.'}, status='401')
    
    try:
        UserSession.objects.get(token = user_token).user.delete
    except:
        return JsonResponse({'error': 'Usuario no encontrado.'}, status='401')
    
    return JsonResponse({'detail': 'Usuario eliminado exitosamente.'}, status='200')

@csrf_exempt
def search_dinosaur(request):
    if request.method != 'GET':
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)

    user_token = request.headers.get('user-token')  # Obtenemos el token del usuario del encabezado de la solicitud

    # Validamos el token del usuario
    if not UserSession.objects.filter(token=user_token).exists():
        return JsonResponse({'Error': 'User not authenticated.'}, status=401)

    # Obtenemos los parámetros de búsqueda de la solicitud
    epoch = request.GET.get('epoch')
    feeding = request.GET.get('feeding')
    environment = request.GET.get('dinosaur_environment')
    dinosaur_name = request.GET.get('name')

    # Buscamos los dinosaurios que cumplen con los criterios de búsqueda
    dinosaurs = Dinosaur.objects.all()

    if epoch:
        dinosaurs = dinosaurs.filter(epoch=epoch)
    if feeding:
        dinosaurs = dinosaurs.filter(feeding=feeding)
    if environment:
        dinosaurs = dinosaurs.filter(dinosaur_environment=environment)
    if dinosaur_name:
        dinosaurs = dinosaurs.filter(name__icontains=dinosaur_name)

    if not dinosaurs:
        return JsonResponse({'error': 'Dinosaur not found.'}, status=404)

    # Obtenemos los datos de los dinosaurios
    dinosaurs_data = [dinosaur.to_json() for dinosaur in dinosaurs]

    return JsonResponse(dinosaurs_data, safe=False, status=200)  # Devolvemos los datos de los dinosaurios con un estado 200

def museums(request):
    if request.method != 'GET':
        return JsonResponse({'error': 'Not supported HTTP method'}, status=405)
    try:
        user_token = request.headers.get('user-token')

    except:
        return JsonResponse({'error': 'Token not found'}, status=401)

    if not UserSession.objects.filter(token=user_token).exists():
        return JsonResponse({'error': 'User not authenticated.'}, status=401)

    try:
        queryset = Museum.objects.all()
    except Museum.DoesNotExist:
        return JsonResponse({"error": "Entry not found"}, status=404)

    json_response = []
    for row in queryset:
        json_response.append(row.to_json())

    return JsonResponse(json_response, safe=False)

@csrf_exempt
def dinosaur(request, id):

    if request.method != 'GET':
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)

    user_token = request.headers.get("user-token")

    if not UserSession.objects.filter(token=user_token).exists():
        return JsonResponse({"Error": "User not autenticated"}, status=401)

    try:
        dinosaur = Dinosaur.objects.get(id=id)  # Buscamos el dinosaurio por su nombre
    except Dinosaur.DoesNotExist:
        return JsonResponse({'error': 'Dinosaur not found.'}, status=404)  # Si el dinosaurio no se encuentra, devolvemos un error 404

    return JsonResponse(dinosaur.to_json(), status=200, safe=False)  # Devolvemos los datos del dinosaurio con un estado 200


@csrf_exempt
def get_favourites(request):
    if request.method != 'GET':
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)

    user_token = request.headers.get("user-token")

    if not UserSession.objects.filter(token=user_token).exists():
        return JsonResponse({"Error": "User not autenticated"}, status=401)

    try:
        user_session = UserSession.objects.get(token=user_token)
    except UserSession.DoesNotExist:
        return JsonResponse({'error': 'Dinosaur not found.'}, status=404)  # Si el dinosaurio no se encuentra, devolvemos un error 404

    try:
        favourites = Favorite.objects.filter(user=user_session.user)
    except UserSession.DoesNotExist:
        return JsonResponse({'error': 'Dinosaur not found.'}, status=404)  # Si el dinosaurio no se encuentra, devolvemos un error 404
    json_response = []
    for favorite in favourites:
        json_response.append(favorite.dinosaur.to_json())

    return JsonResponse(json_response, safe=False)