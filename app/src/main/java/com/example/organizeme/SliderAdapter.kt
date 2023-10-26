import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.organizeme.R

class SliderAdapter(private val context: Context) : PagerAdapter() {

    private val images = intArrayOf(
        R.drawable.organizing_projects_pana,
        R.drawable.studying_pana,
        R.drawable.building_blocks_amico
    )

    private val descriptions = intArrayOf(
        R.string.first_slide_desc,
        R.string.second_slide_desc,
        R.string.third_slide_desc
    )

    private val titles = intArrayOf(
        R.string.first_slide_title,
        R.string.second_slide_title,
        R.string.third_slide_title
    )

    override fun getCount(): Int {
        return titles.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as View
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.slides_layout, container, false)

        val imageView = view.findViewById<ImageView>(R.id.imageSlider)
        val descView = view.findViewById<TextView>(R.id.textSlider)
        val titleView = view.findViewById<TextView>(R.id.titleSlider)

        imageView.setImageResource(images[position])
        descView.text = context.getString(descriptions[position])
        titleView.text = context.getString(titles[position])

        container.addView(view)
        return view
    }

}