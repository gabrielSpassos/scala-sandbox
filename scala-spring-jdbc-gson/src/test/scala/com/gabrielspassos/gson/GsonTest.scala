package com.gabrielspassos.gson

import com.gabrielspassos.DataMock
import com.gabrielspassos.contracts.v1.response.BankResponse
import com.gabrielspassos.dto.{BankDTO, CardDTO, UserDTO, WalletDTO}
import com.google.gson.*
import org.junit.jupiter.api.Assertions.{assertEquals, assertNotNull, assertNull}
import org.junit.jupiter.api.Test

import java.time.LocalDate
import java.util.UUID

class GsonTest {

  @Test
  def shouldReadJsonSuccessfully(): Unit = {
    // given
    val json = """{"code":"600", "name":"Fake bank"}"""

    // when
    val gson = new GsonBuilder().create()
    val bank = gson.fromJson(json, classOf[BankResponse])

    // then
    assertEquals("600", bank.getCode)
    assertEquals("Fake bank", bank.getName)
  }

  @Test
  def shouldReadJsonSuccessfullyWithNullValue(): Unit = {
    // given
    val json = """{"code":"600", "name":null}"""

    // when
    val gson = new GsonBuilder().create()
    val bank = gson.fromJson(json, classOf[BankResponse])

    // then
    assertEquals("600", bank.getCode)
    assertNull(bank.getName)
  }

  @Test
  def shouldReadJsonSuccessfullyWithAllNullValue(): Unit = {
    // given
    val json = """{"code":null, "name":null}"""

    // when
    val gson = new GsonBuilder().create()
    val bank = gson.fromJson(json, classOf[BankResponse])

    // then
    assertNull(bank.getCode)
    assertNull(bank.getName)
  }

  @Test
  def shouldReadJsonSuccessfullyWithImplicitNullValue(): Unit = {
    // given
    val json = """{"code":"600"}"""

    // when
    val gson = new GsonBuilder().create()
    val bank = gson.fromJson(json, classOf[BankResponse])

    // then
    assertEquals("600", bank.getCode)
    assertNull(bank.getName)
  }

  @Test
  def shouldReadJsonSuccessfullyWithImplicitAllNullValue(): Unit = {
    // given
    val json = """{}"""

    // when
    val gson = new GsonBuilder().create()
    val bank = gson.fromJson(json, classOf[BankResponse])

    // then
    assertNull(bank.getCode)
    assertNull(bank.getName)
  }

  @Test
  def shouldParseToJsonSuccessfully(): Unit = {
    // given
    val bankResponse = BankResponse("600", "Fake bank")
    val expectedJson = """{"code":"600","name":"Fake bank"}"""

    // when
    val gson = new GsonBuilder().create()
    val json = gson.toJson(bankResponse)

    // then
    assertEquals(expectedJson, json)
  }

  @Test
  def shouldParseToJsonSuccessfullyWithNullValue(): Unit = {
    // given
    val bankResponse = BankResponse("600", null)
    val expectedJson = """{"code":"600"}"""

    // when
    val gson = new GsonBuilder().create()
    val json = gson.toJson(bankResponse)

    // then
    assertEquals(expectedJson, json)
  }

  @Test
  def shouldParseToJsonSuccessfullyWithAllNullValue(): Unit = {
    // given
    val bankResponse = BankResponse()
    val expectedJson = """{}"""

    // when
    val gson = new GsonBuilder().create()
    val json = gson.toJson(bankResponse)

    // then
    assertEquals(expectedJson, json)
  }

  @Test
  def shouldParseNestedObjectToJsonNestedSuccessfully(): Unit = {
    val bankDTO = BankDTO.toDTO(DataMock.createBankEntity(UUID.fromString("5daca863-65fd-4bbf-b5ff-96c4fffd0cef")))
    val cardDTO = CardDTO.toDTO(DataMock.createCardEntity(UUID.fromString("47135027-da84-4bf7-9f4f-84f31c9f9582"), "5548843996584700", "939"))
    val walletDTO = WalletDTO(
      id = UUID.fromString("e6e93963-676d-4cd0-b324-488812b60d1a"),
      name = "My Wallet",
      cards = List(cardDTO)
    )
    val userDTO = UserDTO(
      id = UUID.fromString("6fa5eeee-780d-4afd-9883-68adbcc1575d"),
      banks = List(bankDTO),
      wallets = List(walletDTO)
    )
    val expected = """{"id":"6fa5eeee-780d-4afd-9883-68adbcc1575d","banks":[{"id":"5daca863-65fd-4bbf-b5ff-96c4fffd0cef","code":"341","name":"Itau"}],"wallets":[{"id":"e6e93963-676d-4cd0-b324-488812b60d1a","name":"My Wallet","cards":[{"id":"47135027-da84-4bf7-9f4f-84f31c9f9582","institutionName":"NuBank","brand":"MasterCard","number":"5548843996584700","name":"Teste Tester","expirationDate":"2028-05-30","cvv":"939"}]}]}"""

    // when
    val gson = new GsonBuilder()
      .registerTypeAdapter(classOf[List[BankDTO]], new ListAdapter[BankDTO])
      .registerTypeAdapter(classOf[List[WalletDTO]], new ListAdapter[WalletDTO])
      .registerTypeAdapter(classOf[List[CardDTO]], new ListAdapter[CardDTO])
      .registerTypeAdapter(classOf[LocalDate], new LocalDateAdapter())
      .create()

    val json = gson.toJson(userDTO)

    assertEquals(expected, json)
  }

  @Test
  def shouldParseList(): Unit = {
    val json = """[{"id":"6fa5eeee-780d-4afd-9883-68adbcc1575d", "code":"600", "name":"Fake bank"}]"""

    val gson = new GsonBuilder()
      .registerTypeAdapter(classOf[List[BankDTO]], new ListAdapter[BankDTO])
      .create()

    val banks = gson.fromJson(json, classOf[List[BankDTO]])

    assertNotNull(banks)
    assertEquals(1, banks.size)
    assertEquals("6fa5eeee-780d-4afd-9883-68adbcc1575d", banks.head.id.toString)
    assertEquals("600", banks.head.code)
    assertEquals("Fake bank", banks.head.name)
  }

  @Test
  def shouldParseJsonToNestedObjectSuccessfully(): Unit = {
    val json = """{"id":"6fa5eeee-780d-4afd-9883-68adbcc1575d","banks":[{"id":"5daca863-65fd-4bbf-b5ff-96c4fffd0cef","code":"341","name":"Itau"}],"wallets":[{"id":"e6e93963-676d-4cd0-b324-488812b60d1a","name":"My Wallet","cards":[{"id":"47135027-da84-4bf7-9f4f-84f31c9f9582","institutionName":"NuBank","brand":"MasterCard","number":"5548843996584700","name":"Teste Tester","expirationDate":"2028-05-30","cvv":"939"}]}]}"""

    // when
    val gson = new GsonBuilder()
      .registerTypeAdapter(classOf[List[?]], new ListAdapter[Any])
      .registerTypeAdapter(classOf[LocalDate], new LocalDateAdapter())
      .create()

    val userDTO = gson.fromJson(json, classOf[UserDTO])

    assertNotNull(userDTO)
    assertNotNull(userDTO.banks)
    assertEquals(1, userDTO.banks.size)
    assertNotNull(userDTO.wallets)
    assertEquals(1, userDTO.wallets.size)
    assertNotNull(userDTO.wallets.head.cards)
    assertEquals(1, userDTO.wallets.head.cards.size)
  }
}
