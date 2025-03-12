"""
URL configuration for backend project.

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/5.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from DododexApi import views
from django.urls import path

from DododexApi import views

urlpatterns = [
    path('admin/', admin.site.urls),
    path('sessions/', views.sessions),
    path('session/', views.session),
    path('register/', views.register),
    path('like/<int:id>/', views.update_dinosaur_like_status),
    path('user_data/', views.user_data),
    path('delete_session/', views.eliminar_cuenta),
    path('search_dinosaur/', views.search_dinosaur),
    path('museums/', views.museums),
    path('dinosaurs/<int:id>/', views.dinosaur),
    path('favourites/', views.get_favourites)
]
