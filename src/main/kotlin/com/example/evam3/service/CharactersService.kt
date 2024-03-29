package com.example.evam3.service

import com.example.evam3.entity.Characters
import com.example.evam3.repository.CharactersRepository
import com.example.evam3.repository.SceneRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
@Service
class CharactersService {
    @Autowired
    lateinit var charactersRepository: CharactersRepository

    @Autowired
     lateinit var sceneRepository: SceneRepository

    fun list (): MutableList<Characters> {
      return charactersRepository.findAll()
    }

    fun save (characters:Characters): Characters {
      try{
        characters.description?.takeIf { it.trim().isNotEmpty() }
          ?: throw Exception("Characters no debe ser vacio")


        return charactersRepository.save(characters)
      }
      catch (ex: Exception){
        throw ResponseStatusException(HttpStatus.BAD_REQUEST,ex.message)
      }

    }
  fun update(characters: Characters): Characters {
    try {
      charactersRepository.findById(characters.id)
        ?: throw Exception("ID no existe")

      val response = sceneRepository.findById(8)
      response

      return charactersRepository.save(characters)
    }
    catch (ex:Exception){
      throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
    }
  }
  fun updateName(characters: Characters): Characters {
    try{
      val response = charactersRepository.findById(characters.id)
        ?: throw Exception("ID no existe")
      response.apply {
        description=characters.description //un atributo del modelo
      }
      return charactersRepository.save(response)
    }
    catch (ex:Exception){
      throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
    }
  }
  fun listById (id:Long?): Characters?{
    return charactersRepository.findById(id)
  }
  fun delete (id: Long?):Boolean?{
    try{
      val response = charactersRepository.findById(id)
        ?: throw Exception("ID no existe")
      charactersRepository.deleteById(id!!)
      return true
    }
    catch (ex:Exception){
      throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
    }
  }
  private fun validateCharacter(characters: Characters) {
    // Obtén la escena asociada al personaje
    val sceneId = characters.sceneId ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El personaje debe estar asociado a una escena")
    val scene = sceneRepository.findById(characters.sceneId)

    // Verifica si el costo del personaje es mayor que el presupuesto de la escena
    if (characters.cost!! > scene.budget!!) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El costo del personaje no puede ser mayor al presupuesto de la escena")
    }
    // Puedes agregar más validaciones según sea necesario
  }
}
