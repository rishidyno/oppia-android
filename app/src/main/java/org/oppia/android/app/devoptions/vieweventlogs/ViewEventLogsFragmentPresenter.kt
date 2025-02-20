package org.oppia.android.app.devoptions.vieweventlogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.oppia.android.app.fragment.FragmentScope
import org.oppia.android.app.recyclerview.BindableAdapter
import org.oppia.android.databinding.ViewEventLogsEventLogItemViewBinding
import org.oppia.android.databinding.ViewEventLogsFragmentBinding
import javax.inject.Inject

/** The presenter for [ViewEventLogsFragment]. */
@FragmentScope
class ViewEventLogsFragmentPresenter @Inject constructor(
  private val activity: AppCompatActivity,
  private val fragment: Fragment,
  private val viewEventLogsViewModel: ViewEventLogsViewModel,
  private val singleTypeBuilderFactory: BindableAdapter.SingleTypeBuilder.Factory
) {

  private lateinit var binding: ViewEventLogsFragmentBinding
  private lateinit var linearLayoutManager: LinearLayoutManager
  private lateinit var bindingAdapter: BindableAdapter<EventLogItemViewModel>

  fun handleCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): View? {
    binding = ViewEventLogsFragmentBinding.inflate(
      inflater,
      container,
      /* attachToRoot= */ false
    )

    binding.viewEventLogsToolbar.setNavigationOnClickListener {
      (activity as ViewEventLogsActivity).finish()
    }

    binding.apply {
      this.lifecycleOwner = fragment
      this.viewModel = viewEventLogsViewModel
    }

    linearLayoutManager = LinearLayoutManager(activity.applicationContext)

    bindingAdapter = createRecyclerViewAdapter()
    binding.viewEventLogsRecyclerView.apply {
      layoutManager = linearLayoutManager
      adapter = bindingAdapter
    }

    return binding.root
  }

  private fun createRecyclerViewAdapter(): BindableAdapter<EventLogItemViewModel> {
    return singleTypeBuilderFactory.create<EventLogItemViewModel>()
      .registerViewDataBinderWithSameModelType(
        inflateDataBinding = ViewEventLogsEventLogItemViewBinding::inflate,
        setViewModel = ViewEventLogsEventLogItemViewBinding::setViewModel
      )
      .build()
  }
}
