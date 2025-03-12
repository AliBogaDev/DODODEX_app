from django.contrib import admin

# Register your models here.

from .models import CustomUser, UserSession, Dinosaur, Museum, Favorite

admin.site.register(CustomUser),
admin.site.register(UserSession),
admin.site.register(Dinosaur),
admin.site.register(Museum),
admin.site.register(Favorite),