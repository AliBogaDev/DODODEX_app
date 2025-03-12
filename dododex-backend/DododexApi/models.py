from django.db import models


# Create your models here.

class CustomUser(models.Model):
    name = models.CharField(max_length=255)
    surname = models.CharField(max_length=255)
    password = models.CharField(max_length=255)
    email = models.EmailField(unique=True)
    telephone = models.CharField(max_length=20, blank=True, null=True)

    def __str__(self):
        return self.name


class UserSession(models.Model):
    user = models.ForeignKey(CustomUser, on_delete=models.CASCADE)
    token = models.CharField(unique=True, max_length=45)


class Dinosaur(models.Model):
    name = models.CharField(max_length=255)
    description = models.TextField()
    feeding = models.CharField(max_length=255, null=True)
    image = models.URLField()
    dinosaur_environment = models.CharField(max_length=255)
    epoch = models.CharField(max_length=255)
    height = models.DecimalField(max_digits=10, decimal_places=2, null=True, blank=True)
    weight = models.DecimalField(max_digits=10, decimal_places=2, null=True, blank=True)

    def __str__(self):
        return self.name

    def to_json(self):
        return {
            "id" : self.id,
            "name" : self.name,
            "image": self.image,
            "feeding": self.feeding,
            "dinosaur_environment": self.dinosaur_environment,
            "epoch": self.epoch,
            "description": self.description,
            "height": self.height,
            "weight" : self.weight,
        }

    def to_json_all(self):
        return {
            "name": self.name,
            "description": self.description,
            "image": self.image,
            "feeding": self.feeding,
            "dinosaur_environment": self.dinosaur_environment,
            "epoch": self.epoch,
            "height": self.height,
            "weight" : self.weight,
        }



class Museum(models.Model):
    name = models.CharField(max_length=255)
    description = models.TextField()
    image = models.URLField()
    location = models.CharField(max_length=255)
    price = models.DecimalField(max_digits=10, decimal_places=2)

    def __str__(self):
        return self.name

    def to_json(self):
        return {
            "name": self.name,
            "description": self.description,
            "image": self.image,
            "location": self.location,
            "price": self.price,
        }


class Favorite(models.Model):
    user = models.ForeignKey(CustomUser, on_delete=models.CASCADE)
    dinosaur = models.ForeignKey(Dinosaur, on_delete=models.CASCADE)

    class Meta:
        unique_together = ('user', 'dinosaur')
