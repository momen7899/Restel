package com.momen.restel.room.viewmodel

import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.AddRoomUseCase
import com.momen.domain.interactor.EditRoomUseCase
import com.momen.domain.interactor.GetRoomUseCase
import com.momen.domain.interactor.RemoveRoomUseCase
import com.momen.restel.room.model.RoomModelDataMapper

class RoomViewModel(
  private val addRoomUseCase: AddRoomUseCase,
  private val editRoomUseCase: EditRoomUseCase,
  private val getRoomsUseCase: GetRoomUseCase,
  private val getRoomUseCase: GetRoomUseCase,
  private val removeRoomUseCase: RemoveRoomUseCase,
  private val roomModelDataMapper: RoomModelDataMapper
) : ViewModel() {

}